package com.xut.controller.user;

import com.qiniu.util.Auth;
import com.xut.bean.Comment;
import com.xut.bean.Reply;
import com.xut.bean.User;
import com.xut.controller.BaseController;
import com.xut.controller.auth.AuthUtil;
import com.xut.controller.data.CommentUIData;
import com.xut.controller.data.ReplyUIData;
import com.xut.filter.Identity;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import com.xut.service.CommentService;
import com.xut.service.ReplyService;
import com.xut.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/comment")
public class UserCommentController extends BaseController {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;
    @Autowired
    UserService userService;

    @GetMapping("/MyComment")
    public ModelAndView myComment(HttpServletRequest request) {
        Map<String, Object> map = getUserModel(request);
        return new ModelAndView("user/myComment", map);
    }

    @GetMapping("/list")
    public Result<Page<CommentUIData>> getCommentList(@RequestParam("typeId") Integer typeId,
                                                      @RequestParam(value = "offset", required = false) Integer offset,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        offset = offset == null || offset < 1 ? 1 : offset;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        Result<Page<CommentUIData>> result = new Result<>();
        Result<List<Comment>> commentsResult = commentService.getComments(typeId, offset, pageSize);
        if (commentsResult.isNotValid()) {
            result.setCode(commentsResult.getCode());
            return result;
        }
        List<Comment> comments = commentsResult.getData();

        List<Integer> userIds = comments.stream().map(Comment::getUserId).collect(Collectors.toList());

        List<Integer> commentIds = comments.stream().map(Comment::getId).collect(Collectors.toList());
        Result<List<Reply>> replysResult = replyService.getReplys(commentIds);
        List<CommentUIData> commentUIDataList = new ArrayList<>();
        if (replysResult.isEmpty()) {
            replysResult.setData(new ArrayList<>());
        }
        userIds.addAll(replysResult.getData().stream().map(Reply::getUserId).collect(Collectors.toList()));
        userIds = userIds.stream().distinct().collect(Collectors.toList());
        Result<List<User>> usersResult = userService.getByIds(userIds);
        if (usersResult.isNotValid()) {
            result.setCode(usersResult.getCode());
            return result;
        }
        Map<Integer, User> userMap = usersResult.getData().stream().collect(Collectors.toMap(User::getId, item -> item));
        Map<Integer, List<Reply>> replyMap = replysResult.getData().stream().collect(Collectors.groupingBy(Reply::getCommentId));
        for (Comment comment : comments) {
            CommentUIData data = new CommentUIData(comment);
            commentUIDataList.add(data);
            data.setAuthor(userMap.get(comment.getUserId()));
            List<Reply> replies = replyMap.get(comment.getId());
            if (CollectionUtils.isEmpty(replies)) {
                continue;
            }
            List<ReplyUIData> replyUIDatas = replies.stream()
                    .sorted((o1, o2) -> Long.compare(o1.getCreatedTime().getTime(), o2.getCreatedTime().getTime()))
                    .collect(Collectors.toList())
                    .stream()
                    .map(item -> new ReplyUIData(item, userMap.get(item.getUserId())))
                    .collect(Collectors.toList());
            data.setReplys(replyUIDatas);
            data.setReplyNum(replies.size());
        }
        Page<CommentUIData> page = new Page<>();
        int count = commentService.geTotalCount(typeId);
        page.setTotalCount(count);
        page.setList(commentUIDataList);
        result.setData(page);
        return result;
    }

    @PostMapping("/create")
    public NoneDataResult create(@RequestBody Comment comment, HttpServletRequest request) {
        NoneDataResult result = new NoneDataResult();
        Identity identity = AuthUtil.getIdentity(request);
        if (identity == null) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        result = commentService.create(comment);
        return result;
    }

    @PostMapping("/delete")
    public NoneDataResult delete(@RequestParam("id") Integer id, HttpServletRequest request) {
        NoneDataResult result = commentService.delete(id);
        if (result.isNotOK()) {
            return result;
        }
        Identity identity = AuthUtil.getIdentity(request);
        if (identity == null) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        Result<Comment> commentResult = commentService.getById(id);
        if (commentResult.isNotValid()) {
            return result;
        }
        if (!commentResult.getData().getUserId().equals(identity.getUserId())) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        result = commentService.delete(id);
        if (result.isNotOK()) {
            return result;
        }
        result = replyService.delete(null, id);
        return result;
    }

}
