$(function() {
  // 상단부 고정
  $('#nav').scrollToFixed();
  $('.res-nav_click').click(function() {
    $('.main-nav').slideToggle();
    return false;
  });
  $('#header').css('z-index:1001;');

  // Easing effect
  $('.main-nav li a, .servicelink').bind('click', function(event) {
    let $anchor = $(this);
    $('html, body').stop().animate({
      scrollTop: $($anchor.attr('href')).offset().top - 102,
    }, 1500, 'easeInOutExpo');
    if ($(window).width() < 768) {
      $('.main-nav').hide();
    }
    event.preventDefault();
  });

  // Search-Form
  $('.search-panel span#search_concept').text('전체');
  $('.search-panel .dropdown-menu').find('a').click(function(e) {
    e.preventDefault();
//  var param = $(this).attr('href').replace('#', '');
    let concept = $(this).text();
    $('.search-panel span#search_concept').text(concept);
    /* $('.input-group #search_param').val(param);*/
  });

  // Login
  let login = function(e) {
    e.stopPropagation(e);
    // TODO: 유효성 검사

    $.ajax({
      type: 'POST',
      url: '/login',
      data: {
        'email': $('#login-email').val(),
        'pwd': $('#login-password').val(),
      },
      success: function(resp) {
        if (resp.result=='n') {
          $('#login-alert').slideDown(250).delay(1500).slideUp(250);
        } else {
          $('#login-form').dropdown('toggle');
          location.reload();
        }
      },
    });
  };
  $('#login-form').bind('click', function(e) {
    e.stopPropagation();
  });
  $('#login-alert').hide();
  $('#login-btn').click(function(e) {
    login(e);
  });
  $('#login-email, #login-password').keypress(function(e) {
    if (e.keyCode == '13') {
      login(e);
    }
  });

  $('#modify-btn').click(function() {
    location.href='/signup/addinfo';
  });
  $('#logout-btn').click(function() {
    $.ajax({
      type: 'GET',
      url: '/logout',
      success: function(resp) {
        if (resp.result=='y') {
          location.reload();
        }
      },
    });
  });


  // 검색조건 설정
  $('.dropdown-menu li').click(function() {
    let searchParam=$(this).text();

    // alert('searchParam = '+searchParam);
    $('#search_concept').text(searchParam);
    $('#searchParam').val(searchParam);
    $('#searchKeyword').focus();

  });

  // 검색정보 보내기
  // 이걸 따로 안쓰면 form의 action주소 적혀있는곳으로 바로 가버린다.
  $('#searchKeyword').keydown(function(key) {
        if (key.keyCode == 13) {// 키가 13이면 실행 (엔터는 13)
          send();
        }
    });

  // 검색정보 보내기
  $('#searchSend').click(function() {
    send();

  });

  function send() {
    // 유효성검사
    if ($('#searchKeyword').val().trim()=='') {
      // alert("검색어를 입력하세요");
      $('#searchKeyword').focus();
      return;
    }

    if ($('#searchParam').val()=='') {
      $('#searchParam').val('전체');
    }


    if ($('#searchParam').val()=='전체') {
      $('#search_form').attr('action', '/search/search_total_result');
      $('#search_form').submit();
    } else {
      $('#search_form').attr('action', '/search/search_result');
      $('#search_form').submit();
    }
  }
});
