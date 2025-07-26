"use strict";

var html = document.querySelector("html");
var body = document.querySelector("body");
var Sidebar = {
  init: function init() {
    this.toggle();
  },
  toggle: function toggle() {
    var btnMenu = document.querySelector(".btn-sidebar-toggler");
    if (btnMenu) {
      btnMenu.addEventListener("click", function () {
        html.classList.toggle("sidebar-collapsed");
      });
    }
  }
};
var Navbar = {
  init: function init() {
    this.toggle();
  },
  toggle: function toggle() {
    var btnAllmenu = document.querySelector(".btn-allmenu");
    if (btnAllmenu) {
      btnAllmenu.addEventListener("click", function () {
        html.classList.add("allmenu-show");
      });
    }
    var btnAllmenuClose = document.querySelector(".btn-allmenu-close");
    if (btnAllmenuClose) {
      btnAllmenuClose.addEventListener("click", function () {
        html.classList.remove("allmenu-show");
      });
    }
  }
};
var Common = {
  init: function init() {
    this.picker();
    this.inputDel();
  },
  picker: function picker() {
    var datePickerInputs = [];
    $('[data-picker="date"]').each(function (i) {
      // eslint-disable-next-line no-undef
      datePickerInputs[i] = new tempusDominus.TempusDominus($(this)[0], {
        display: {
          theme: "light",
          viewMode: "calendar",
          components: {
            clock: false
          }
        }
      });
      // eslint-disable-next-line no-undef
      datePickerInputs[i].dates.formatInput = function (date) {
        return moment(date).format("YYYY.MM.DD");
      };
    });

    //datetimepicker
    var dateTimePickerInputs = [];
    $('[data-picker="datetime"]').each(function (i) {
      // eslint-disable-next-line no-undef
      dateTimePickerInputs[i] = new tempusDominus.TempusDominus($(this)[0], {
        display: {
          sideBySide: true
        }
      });
      // eslint-disable-next-line no-undef
      dateTimePickerInputs[i].dates.formatInput = function (date) {
        return moment(date).format("YYYY.MM.DD HH:mm");
      };
    });

    //timepicker
    $('[data-picker="color"]').each(function (i) {
      $(this).spectrum({
        type: "component",
        showPalette: true,
        togglePaletteOnly: true
      });
    });
  },
  inputDel: function inputDel() {
    // input del표시
    var inputClear = document.querySelectorAll(".form-control-wrapper input");
    inputClear.forEach(function (input) {
      input.addEventListener("focus", function () {
        if (this.value.length > 0) {
          this.classList.add("filled");
        } else {
          this.classList.remove("filled");
        }
        input.addEventListener("keyup", function () {
          if (this.value.length > 0) {
            this.classList.add("filled");
          } else {
            this.classList.remove("filled");
          }
        });
      });
      input.addEventListener("focusout", function () {
        setTimeout(function () {
          input.classList.remove("filled");
        }, 100);
      });
    });
    var btnClear = document.querySelectorAll(".icon-del");
    btnClear.forEach(function (btn) {
      btn.addEventListener("click", function () {
        this.previousElementSibling.value = "";
        this.previousElementSibling.classList.remove("filled");
      });
    });
  }
};
Sidebar.init();
Navbar.init();
Common.init();