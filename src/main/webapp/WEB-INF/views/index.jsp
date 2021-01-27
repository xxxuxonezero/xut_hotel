<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
    <div class="swiper-container">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <img src="<c:url value="/resources/images/index1.jpeg"/> " alt="">
            </div>
            <div class="swiper-slide">
                <img src="<c:url value="/resources/images/index2.jpeg"/>" alt="">
            </div>
            <div class="swiper-slide">
                <img src="<c:url value="/resources/images/index3.jpeg"/>" alt="">
            </div>
        </div>
        <div class="swiper-button-prev"></div><!--左箭头。如果放置在swiper-container外面，需要自定义样式。-->
        <div class="swiper-button-next"></div><!--右箭头。如果放置在swiper-container外面，需要自定义样式。-->
    </div>
    <div id="hotelInformation" class="mt16 ml16">
        <div class="information-title-bar">
            <div class="line-icon inline-block"></div>
            <span class="information-title">酒店信息</span>
        </div>
        <div class="card ml16 mr16">
            <div class="card-body">
                <div>地址：南京市浦口区南京信息工程大学</div>
                <div>前台电话：15812364856</div>
            </div>
        </div>
    </div>
    <div id="hotelIntroduction">
        <div class="information-title-bar">
            <div class="line-icon inline-block"></div>
            <span class="information-title">酒店介绍</span>
        </div>
        <div class="card ml16 mr16 mb16">
            <div class="card-body">
                <div>
                    ***酒店是一家bai高档商务酒店。酒du店地处***商贸中心，位zhi于**街与**大道相交dao处，酒店紧zhuan靠***
                    　　***酒店装修时尚高雅，设施齐全，环境舒适。拥有跃层商务客房，酒店还配有豪华餐饮包厢、中西自助餐厅、会议厅、商务中心、精品屋、美容美发和足浴中心等，服务配套与娱乐设施一应俱全。酒店以“宾客至上，服务第一”为经营宗旨，采用了科学的经营机制和管理方法，不断追求卓越，得到了社会的认可，更被省有关部门确定**接待酒店。无论商务、宴会、休闲、娱乐，都是您的理想之选。
                    　　田园大酒店，诚心恭候您的光临！
                    <br>
                    酒店交通：
                    <br>
                    距离机场**km，距离汽车站、火车站**米，乘坐**公交车，打的**
                </div>
            </div>
        </div>
    </div>

<jsp:include page="footer.jsp"></jsp:include>
</body>
<script>
    var swiper = new Swiper('.swiper-container',{
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        }
    });

</script>
</html>
