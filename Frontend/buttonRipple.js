var rippleEffect = (function () {
  var className, ripple;

  className = "Button";
  ripple = document.createElement("div");
  ripple.classList.add("ripple");

  document.addEventListener("mousedown", function (e) {
    if (e.target.classList.contains(className)) {
      ripple.setAttribute(
        "style",
        "top: " + e.offsetY + "px; left: " + e.offsetX + "px"
      );
      e.target.appendChild(ripple);
    }
  });
})();
