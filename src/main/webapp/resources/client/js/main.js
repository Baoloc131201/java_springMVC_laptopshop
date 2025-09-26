(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner(0);


    // Fixed Navbar
    $(window).scroll(function () {
        if ($(window).width() < 992) {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow');
            } else {
                $('.fixed-top').removeClass('shadow');
            }
        } else {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow').css('top', -10);
            } else {
                $('.fixed-top').removeClass('shadow').css('top', 0);
            }
        } 
    });
    
    
   // Back to top button
   $(window).scroll(function () {
    if ($(this).scrollTop() > 300) {
        $('.back-to-top').fadeIn('slow');
    } else {
        $('.back-to-top').fadeOut('slow');
    }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 2000,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav : true,
        navText : [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0:{
                items:1
            },
            576:{
                items:1
            },
            768:{
                items:1
            },
            992:{
                items:2
            },
            1200:{
                items:2
            }
        }
    });


    // vegetable carousel
    $(".vegetable-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav : true,
        navText : [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0:{
                items:1
            },
            576:{
                items:1
            },
            768:{
                items:2
            },
            992:{
                items:3
            },
            1200:{
                items:4
            }
        }
    });


    // Modal Video
    $(document).ready(function () {
        var $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        console.log($videoSrc);

        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        })
    });

     function getCookie(name) {
         let value = `; ${document.cookie}`;
         let parts = value.split(`; ${name}=`);
         if (parts.length === 2) return parts.pop().split(';').shift();
         return null;
     }

       var csrfToken = getCookie('XSRF-TOKEN');

       console.log(csrfToken);

    // Product Quantity
    $('.quantity button').on('click', function () {
        let quantityField = $(this).closest('tr').find('input[type="text"]');
        let button = $(this);
        let oldValue = parseFloat(quantityField.val());
        let change = 0;

        let newVal = oldValue;
        if (button.hasClass('btn-plus')) {
            newVal = oldValue + 1;
            change = 1;
        } else {
            if (oldValue > 1) {
                newVal = oldValue - 1;
                change = -1;
            }
        }

        quantityField.val(newVal);

        // set form hidden index field
        const index = quantityField.attr("data-cart-detail-index");
        const el = document.getElementById(`cartDetails${index}.quantity`);
        if (el) $(el).val(newVal);

        // get price and id
        const price = parseFloat(quantityField.attr("data-cart-detail-price"));
        const id = quantityField.attr("data-cart-detail-id");
        const priceElement = $(`p[data-cart-detail-id='${id}']`);

        // calculate new item price
        let newPrice = price * newVal;
        priceElement.text(formatCurrency(newPrice) + " đ");

        // update total cart price
        let grandTotal = 0;
        $("p.total-price").each(function () {
            const priceText = $(this).text().replace(/[^\d]/g, '');
            if (priceText) {
                grandTotal += parseFloat(priceText);
            }
        });

        // Cập nhật cả 2 vị trí hiển thị tổng
        $("#temporary-total-price, #final-total-price").each(function () {
            $(this).text(formatCurrency(grandTotal) + " đ");
            $(this).attr("data-cart-total-price", grandTotal);
        });

        // update server via AJAX
        const cartDetailId = quantityField.data('cart-detail-id');

        if (!csrfToken) {
            console.error("CSRF token not found");
            return;
        }

        $.ajax({
            url: '/update-cart-product',
            type: 'POST',
            data: {
                cartDetailId: cartDetailId,
                quantity: newVal,
                _csrf: csrfToken
            },
            success: function (response) {
                alert('Cart updated successfully');
            },
            error: function (xhr, status, error) {
                console.log("Error Details:", xhr.responseText);
                alert('Error updating cart');
            }
        });
    });

     function formatCurrency(value) {
            // and 'VND' as the currency type for Vietnamese đồng
            const formatter = new Intl.NumberFormat('vi-VN', {
                style: 'decimal',
                minimumFractionDigits: 0, // No decimal part for whole numbers
            });

            let formatted = formatter.format(value);
            // Replace dots with commas for thousands separator
            formatted = formatted.replace(/\./g, ',');
            return formatted;
     }

    //handle filter products
    $('#btnFilter').click(function (event) {
        event.preventDefault();

        let factoryArr = [];
        let targetArr = [];
        let priceArr = [];
        //factory filter
        $("#factoryFilter .form-check-input:checked").each(function () {
            factoryArr.push($(this).val());
        });

        //target filter
        $("#targetFilter .form-check-input:checked").each(function () {
            targetArr.push($(this).val());
        });

        //price filter
        $("#priceFilter .form-check-input:checked").each(function () {
            priceArr.push($(this).val());
        });

        //sort order
        let sortValue = $('input[name="radio-sort"]:checked').val();

        const currentUrl = new URL(window.location.href);
        const searchParams = currentUrl.searchParams;

        // Add or update query parameters
        searchParams.set('page', '1');
        searchParams.set('sort', sortValue);

        //reset
        searchParams.delete('factory');
        searchParams.delete('target');
        searchParams.delete('price');

        if (factoryArr.length > 0) {
            searchParams.set('factory', factoryArr.join(','));
        }

        if (targetArr.length > 0) {
            searchParams.set('target', targetArr.join(','));
        }

        if (priceArr.length > 0) {
            searchParams.set('price', priceArr.join(','));
        }

        // Update the URL and reload the page
        window.location.href = currentUrl.toString();
    });

    //handle auto checkbox after page loading
    // Parse the URL parameters
    const params = new URLSearchParams(window.location.search);

    // Set checkboxes for 'factory'
    if (params.has('factory')) {
        const factories = params.get('factory').split(',');
        factories.forEach(factory => {
            $(`#factoryFilter .form-check-input[value="${factory}"]`).prop('checked', true);
        });
    }

    // Set checkboxes for 'target'
    if (params.has('target')) {
        const targets = params.get('target').split(',');
        targets.forEach(target => {
            $(`#targetFilter .form-check-input[value="${target}"]`).prop('checked', true);
        });
    }

    // Set checkboxes for 'price'
    if (params.has('price')) {
        const prices = params.get('price').split(',');
        prices.forEach(price => {
            $(`#priceFilter .form-check-input[value="${price}"]`).prop('checked', true);
        });
    }

    // Set radio buttons for 'sort'
    if (params.has('sort')) {
        const sort = params.get('sort');
        $(`input[type="radio"][name="radio-sort"][value="${sort}"]`).prop('checked', true);
    }

})(jQuery);



