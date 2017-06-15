'use strict';
!function($){
    $(document).ready(function(){
        $.each($("[data-scroll-to]"),function(i,o){
            o= $(o);
            var scoreTo = o.data('scrollTo');
            if( $(scoreTo).position){
              $(o).data('scrollToPosition',$(scoreTo).position().top);
              $(o).on('click',function(){
                  var content = $(".mdl-layout__content");
                  var target = $(this).data('scrollToPosition');
                  !!target&&content.stop().animate({ scrollTop: target }, "slow");
              });
            }
        });
    });
}(jQuery);
