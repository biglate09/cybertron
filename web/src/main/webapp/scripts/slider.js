'use strict';
(function(){
  var _root = document.querySelector(".oli-gallery-container");
  var slides = _root.querySelectorAll("div.slide");
  var indicators = [];
  var activeSlide= null;
  var indicatorsActive = null;
  var at = 0;
  var _count = slides.length||0;

  //init
  initSlide();



  function initSlide(){
    if(_count>0){
    addIndicator();
    //setSlide(at);
    slides[0].className="slide active";
    }
  }
  function addIndicator(){
     var in_container = document.createElement("DIV");
     in_container.className="oli-slider_indicator_container";
     _root.appendChild(in_container);
      for(var i =0,ind=newIndicator(i) ;i < _count;(ind = newIndicator(++i))){
          indicators[i] = ind;
          in_container.appendChild(ind);
      }
  }
  function newIndicator(index){
      var indicator = document.createElement("span");
      indicator.className="indicator";
      indicator.addEventListener('click',setSlide.bind(indicator,index));
      return indicator;
  }
  function setSlide(num){
    activeSlide&&(activeSlide.className="slide prev");
    (activeSlide =slides[num]).className="slide active";
    hasNext(num)&&(slides[getNext(num)].className = "slide next");
    hasPrev(num)&&(slides[getPrev(num)].className = "slide prev");
    indicatorsActive&&(indicatorsActive.className = "indicator");
    (indicatorsActive = indicators[num]).className = "indicator active";
  }
  function hasNext(num){
      return (_count>2&&_count>=num)||(_count>1&&num+1<_count);
  }
  function getNext(num){
      return num+1>=_count?0:(num+1);
  }
  function hasPrev(num){
      return (_count>2)||(_count>1&&num>0);
  }
  function getPrev(num){
    return num-1<0?_count-1:(num-1);
  }
})();
