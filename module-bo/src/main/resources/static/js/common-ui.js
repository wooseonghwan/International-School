var html = document.querySelector("html");
var body = document.querySelector("body");

let Sidebar = {
  init: function () {
    this.toggle();
  },
  toggle: function () {
    const btnMenu = document.querySelector(".sidebar-toggler");
    btnMenu?.addEventListener("click", function () {
      html.classList.toggle("sidebar-collapsed");
    });
  },
};

let Navbar = {
  init: function () {
    this.toggle();
  },
  toggle: function () {
    const btnAllmenu = document.querySelector(".btn-allmenu");
    btnAllmenu?.addEventListener("click", function () {
      html.classList.add("sidebar-show");
    });
    const btnAllmenuClose = document.querySelector(".btn-allmenu-close");
    btnAllmenuClose?.addEventListener("click", function () {
      html.classList.remove("sidebar-show");
    });
  },
};

let Common = {
  init: function () {
    this.customSelect();
    this.datepicker();
    this.checkDatepicker();
    this.pageScroll();
    this.imgPreview();
    this.inputDelete();
    this.inputSearch();
    this.buttonDatepicker();
    this.selectDate();
  },
  customSelect: function () {
    /* document.querySelectorAll(".form-select").forEach((el) => {
      let settings = {};
      new TomSelect(el, settings);
    }); */
    // $(".form-select").select2({
    //   minimumResultsForSearch: -1,
    // });
  },
  datepicker: function () {
    $("[data-picker='date']").datepicker({
      dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
      monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
      monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
      changeYear: true,
      changeMonth: true,
      showMonthAfterYear: true,
      //yearSuffix: "년",
      dateFormat: "yy-mm-dd",
    });
  },
  buttonDatepicker: function () {
      $("[data-value-picker='date']").datepicker({
        dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
        monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
        monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
        changeYear: true,
        changeMonth: true,
        showMonthAfterYear: true,
        dateFormat: "yy-mm-dd",
//        onSelect: function (dateText, inst) {
//          // 선택된 날짜를 무효화
////          $(this).val("시작일 변경");
//        },
      });
    },
  selectDate: function () {
    $(".date-box").click(function () {
      if($(this).hasClass("active")){
          $(this).removeClass("active");
      }else{
          $(this).addClass("active");
      }
    });
  },

  checkDatepicker: function () {
    function addCustomElements() {
      $("td").each(function () {
        var day = parseInt($(this).text(), 10);
        if (day >= 1 && day <= 14 && !$(this).find(".semi-peak-dot").length) {
          $(this).append('<div class="semi-peak-dot"></div>');
        } else if (day >= 15 && day <= 21 && !$(this).find(".peak-dot").length) {
          $(this).append('<div class="peak-dot"></div>');
        } else if (day >= 22 && day <= 25 && !$(this).find(".christmas-dot").length) {
          $(this).append('<div class="christmas-dot"></div>');
        }
      });
      if (!$(".ui-datepicker-calendar").next(".legend").length) {
        $(".ui-datepicker-calendar").after(`
            <div class="legend">
                <div class="legend-item semi-peak"><span class="semi-peak"></span>준성수기</div>
                <div class="legend-item peak"><span class="peak"></span>성수기</div>
                <div class="legend-item christmas"><span class="christmas"></span>크리스마스</div>
            </div>
        `);
      }
    }

    $("[data-check-picker='date']").datepicker({
      dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
      monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
      monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
      changeYear: true,
      changeMonth: true,
      showMonthAfterYear: true,
      dateFormat: "yy-mm-dd",
      beforeShowDay: function (date) {
        var day = date.getDate();
        if (day >= 1 && day <= 14) {
          return [true, "semi-peak"];
        } else if (day >= 15 && day <= 21) {
          return [true, "peak"];
        } else if (day >= 22 && day <= 25) {
          return [true, "christmas"];
        }
        return [true, ""];
      },
      onChangeMonthYear: function (year, month, inst) {
        setTimeout(addCustomElements, 10); // 약간의 지연을 추가하여 div가 제대로 추가되도록 합니다.
      },
      onSelect: function (dateText, inst) {
        setTimeout(addCustomElements, 10); // 약간의 지연을 추가하여 div가 제대로 추가되도록 합니다.
      },
    });

    // 달력을 처음 열 때 div가 추가되도록 하는 함수
    $("[data-check-picker='date']").click(function () {
      setTimeout(addCustomElements, 10);
    });

    // 초기 로드 시에도 div를 추가
    setTimeout(addCustomElements, 10);
  },
  pageScroll: function () {
    const buttons = document.querySelectorAll(".scroll-tabs button");
    for (const button of buttons) {
      button.addEventListener("click", function () {
        //선택된 button의 dataset.target명의 offsetTop을 구하는 변수를 선언한다.
        const headerHeight = document.querySelector("#navbar").offsetHeight;
        const scrollPosition = document.querySelector(this.dataset.scroll).offsetTop - headerHeight;

        //scroll을 offsetTop으로 이동시킨다.
        window.scrollTo({ top: scrollPosition, behavior: "smooth" });
      });
    }
  },
  imgPreview: function () {
    //input에 파일이 들어가있을 때
    $("#modal-photo-fill").on("show.bs.modal", function (event) {
      var button = $(event.relatedTarget); // 모달을 열게 한 요소 (버튼)을 가져옴
      var inputFieldValue = button.closest(".photo-fill-wrap").find("input.form-control").val(); // 해당 버튼이 속한 row의 input 값을 가져옴
      var modal = $(this); // 모달 자체를 가져옴
      var imageUrl = "../../../../assets/images/" + inputFieldValue; // 이미지의 URL을 생성
      modal.find(".modal-body img").attr("src", imageUrl); // 모달 내의 이미지 소스를 설정
    });

    $("#modal-photo-fill").on("hidden.bs.modal", function (event) {
      var modal = $(this); // 모달 자체를 가져옴
      modal.find(".modal-body img").attr("src", ""); // 모달 내의 이미지 소스를 설정
    });
  },
  inputDelete: function () {
    $(".input-form .form-control").on("input", function () {
      var $parent = $(this).closest(".form-field");
      var $button = $parent.find(".clear-button");
      if ($(this).val().length > 0) {
        $button.show();
      } else {
        $button.hide();
      }
    });

    $(".clear-button").on("click", function () {
      var $parent = $(this).closest(".form-field");
      var $input = $parent.find(".input-form .form-control");
      $input.val("");
      $(this).hide();
    });
  },
  inputSearch: function () {
    $(".search-form").on("input", function () {
      // 입력된 텍스트가 있는지 확인
      if ($(this).val().trim().length > 0) {
        // 텍스트가 있으면 검색 결과를 보여줌
        $(".search-result-box").show();
      } else {
        // 텍스트가 없으면 검색 결과를 숨김
        $(".search-result-box").hide();
      }
    });

    // 클리어 버튼 클릭 시 입력란 비우고 검색 결과를 숨김
    $(".clear-button").on("click", function () {
      $(".search-form").val(""); // 입력란 비우기
      $(".search-result-box").hide(); // 검색 결과 숨김
    });
  },
};

Sidebar.init();
Navbar.init();
Common.init();
