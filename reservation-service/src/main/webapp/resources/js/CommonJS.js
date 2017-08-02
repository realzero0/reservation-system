(function (jquery, eg) {
    var $ = jquery;
    function extend(superClass, def) {
        var extendClass = function extendClass() {
            // Call a parent constructor
            superClass.apply(this, arguments);

            // Call a child constructor
            if (typeof def.init === "function") {
                def.init.apply(this, arguments);
            }
        };

        var ExtProto = function () {
        };
        ExtProto.prototype = superClass.prototype;

        var extProto = new ExtProto();
        for (var i in def) {
            extProto[i] = def[i];
        }
        extProto.constructor = extendClass;
        extendClass.prototype = extProto;

        return extendClass;
    }


    var ConvertTimestamp = eg.Class({
        "construct": function (timestamp) {
            this.timestamp = timestamp;
        },
        "convert": function (timestamp) {
            var time;
            var d = new Date(timestamp);
            var yyyy = d.getFullYear();
            var mm = ('0' + (d.getMonth() + 1)).slice(-2); // Months are zero based. Add leading 0.
            var dd = ('0' + d.getDate()).slice(-2); // Add leading 0.
            var hh = d.getHours();
            var h = hh;
            var min = ('0' + d.getMinutes()).slice(-2); // Add leading 0.
            var ampm = 'AM';
            if (hh > 12) {
                h = hh - 12;
                ampm = 'PM';
            } else if (hh === 12) {
                h = 12;
                ampm = 'PM';
            } else if (hh == 0) {
                h = 12;
            }
            time = yyyy + '-' + mm + '-' + dd + ', ' + h + ':' + min + ' ' + ampm;
            return time;
        }
    });

    function ImgRoller($findElement) {
        var $ulEle = $findElement.find('ul');
        var $prev = $('.btn_prev');
        var $next = $('.btn_nxt');

        var isDragging = false;
        var curX;
        var images;
        var curImage;
        var count;
        var commentId;
        var dX;
        var autoMoveSize;

        $prev.on({
            'click': function () {
                if (clickState === false) {
                    if (curImage > 1) {
                        curImage--;
                        translate(curImage);
                    }
                }
            }
        });
        $next.on({
            'click': function () {
                if (clickState === false) {
                    if (curImage < pages) {
                        curImage++;
                        translate(currentPage);
                    }
                }
            }
        });

        function init($th) {
            curImage = 0;
            $countElement = $('.img-popup-layer.count span');
            images = new Array();
            commentId = $th.attr('id');
            count = $th.siblings('.img_count').text();
            $countElement.text('1 / ' + count);
        }

        $thumb.on('click', function (e) {
            init($(this));
            $('div.img-popup-layer').show();
            popupImgList();
        });
        $('.img-popup-layer.exit').on('click', function () {
            $('div.img-popup-layer').hide();
            removeLi();
        });

        function popupImgList() {
            $.ajax({
                type: 'GET',
                url: '/api/comments/pictures/' + commentId,
                contentType: 'application/json',
                success: function (res) {
                    for (var i = 0; i < res.length; i++) {
                        images[i] = res[i];
                    }
                    makeList(res);
                    bindtouchEvent();
                }
            });
            $ulEle.css({
                "left": 0 + "%"
            });
        }

        function removeLi() {
            $ulEle.children().remove();
        }

        function makeList(res) {
            var liEle = [];
            for (var i = 0; i < res.length; i++) {
                images[i] = res[i];
                liEle[i] = itemTemplate({
                    'fileLocation': 'http://220.230.122.151/img/' + images[i]
                });
            }
            liEle = liEle.join('');
            $findElement.find('ul').append(liEle);
            move = $ulEle.width();
            autoMoveSize = $ulEle.width() / 3;
        }

        function bindtouchEvent() {
            $ulEle.on('mousedown touchstart', function (e) {
                isDragging = true;
                if (e.type == 'touchstart') {
                    e = e.touches[0];
                }
                curX = e.clientX;
                console.log('curX = ' + curX);
            });
            $ulEle.on('mousemove touchmove', function (e) {
                if (isDragging) {
                    if (e.type == 'touchmove') {
                        e = e.touches[0];
                    }
                    dX = e.clientX - curX;
                    $ulEle.css({
                        "left": -curImage * 100 + (dX / 30) + "%"
                    });
                }
            });
            $ulEle.on('dragstart', function () {
                return false;
            });
            $(document).on('mouseup touchend', function (e) {
                if (isDragging) {
                    isDragging = false;
                    console.log('dX = ' + dX);
                    if (dX < -20 || 20 < dX) {
                        console.log("ddd");
                        if (!imageMove(e)) {
                            return false;
                        } else {
                            console.log("ffff");
                            $ulEle.css({
                                "left": -curImage * 100 + "%"
                            });
                        }
                    }
                }
            });
        }

        function imageMove(e) {
            var inDecre = -dX / Math.abs(dX);
            curImage += inDecre;
            if (curImage < count && curImage >= 0) {
                $countElement.text((curImage + 1) + ' / ' + count);
                isDragging = false;
                $ulEle.animate({
                    "left": -curImage * 100 + "%"
                }, 'normal');
            } else {
                curImage -= inDecre;
                $ulEle.animate({
                    "left": -curImage * 100 + "%"
                }, 'normal');
                return false;
            }
            if (e.type == 'mousemove') {
                e.preventDefault();
            }
        }

    }

    var PopUp = (function () {
        var $findElement = $('.img-popup-layer.img-viewer');
        var $countElement;
        var itemSource = $('#popup-img-template').html();
        var itemTemplate = Handlebars.compile(itemSource);
        var $ulEle = $findElement.find('ul');
        var isDragging = false;
        var $thumb;
        var curX;
        var images;
        var curImage;
        var count;
        var commentId;
        var dX;
        var autoMoveSize;

        function init($th) {
            curImage = 0;
            $countElement = $('.img-popup-layer.count span');
            images = new Array();
            commentId = $th.attr('id');
            count = $th.siblings('.img_count').text();
            $countElement.text('1 / ' + count);
        }

        function popupImgList() {
            $.ajax({
                type: 'GET',
                url: '/api/comments/pictures/' + commentId,
                contentType: 'application/json',
                success: function (res) {
                    for (var i = 0; i < res.length; i++) {
                        images[i] = res[i];
                    }
                    makeList(res);
                    bindtouchEvent();
                }
            });
            $ulEle.css({
                "left": 0 + "%"
            });
        }

        function removeLi() {
            $ulEle.children().remove();
        }

        function makeList(res) {
            var liEle = [];
            for (var i = 0; i < res.length; i++) {
                images[i] = res[i];
                liEle[i] = itemTemplate({
                    'fileLocation': 'http://220.230.122.151/img/' + images[i]
                });
            }
            liEle = liEle.join('');
            $findElement.find('ul').append(liEle);
            move = $ulEle.width();
            autoMoveSize = $ulEle.width() / 3;
        }

        function bindtouchEvent() {
            $ulEle.on('mousedown touchstart', function (e) {
                isDragging = true;
                if (e.type == 'touchstart') {
                    e = e.touches[0];
                }
                curX = e.clientX;
                console.log('curX = ' + curX);
            });
            $ulEle.on('mousemove touchmove', function (e) {
                if (isDragging) {
                    if (e.type == 'touchmove') {
                        e = e.touches[0];
                    }
                    dX = e.clientX - curX;
                    $ulEle.css({
                        "left": -curImage * 100 + (dX / 30) + "%"
                    });
                }
            });
            $ulEle.on('dragstart', function () {
                return false;
            });
            $(document).on('mouseup touchend', function (e) {
                if (isDragging) {
                    isDragging = false;
                    console.log('dX = ' + dX);
                    if (dX < -20 || 20 < dX) {
                        console.log("ddd");
                        if (!imageMove(e)) {
                            return false;
                        } else {
                            console.log("ffff");
                            $ulEle.css({
                                "left": -curImage * 100 + "%"
                            });
                        }
                    }
                }
            });
        }

        function imageMove(e) {
            var inDecre = -dX / Math.abs(dX);
            curImage += inDecre;
            if (curImage < count && curImage >= 0) {
                $countElement.text((curImage + 1) + ' / ' + count);
                isDragging = false;
                $ulEle.animate({
                    "left": -curImage * 100 + "%"
                }, 'normal');
            } else {
                curImage -= inDecre;
                $ulEle.animate({
                    "left": -curImage * 100 + "%"
                }, 'normal');
                return false;
            }
            if (e.type == 'mousemove') {
                e.preventDefault();
            }
        }

        return function ($thumb) {
            this.$thumb = $thumb;
            $thumb.on('click', function (e) {
                init($(this));
                $('div.img-popup-layer').show();
                popupImgList();
            });
            $('.img-popup-layer.exit').on('click', function () {
                $('div.img-popup-layer').hide();
                removeLi();
            });
        }
    })();
})(jquery, eg);


