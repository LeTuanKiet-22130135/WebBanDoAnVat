/**
 * JavaScript for handling AJAX cart operations
 */
$(document).ready(function() {
    // Function to update cart items via AJAX
    function updateCartItem(productId, variantId, action) {
        $.ajax({
            url: 'UpdateCartServlet',
            type: 'POST',
            data: {
                productId: productId,
                variantId: variantId,
                action: action
            },
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            },
            success: function(response) {
                if (response.success) {
                    // Reload cart items fragment
                    reloadCartItems();
                    // Update cart summary
                    updateCartSummary(response.cartSubtotal);
                    // Update cart count in header
                    updateCartCount(response.cartItemCount);
                } else {
                    alert('Error: ' + response.message);
                }
            },
            error: function() {
                alert('An error occurred while updating the cart.');
            }
        });
    }

    // Function to remove item from cart via AJAX
    function removeCartItem(productId, variantId) {
        $.ajax({
            url: 'RemoveFromCartServlet',
            type: 'GET',
            data: {
                productId: productId,
                variantId: variantId
            },
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            },
            success: function(response) {
                if (response.success) {
                    // Reload cart items fragment
                    reloadCartItems();
                    // Update cart summary
                    updateCartSummary(response.cartSubtotal);
                    // Update cart count in header
                    updateCartCount(response.cartItemCount);
                } else {
                    alert('Error: ' + response.message);
                }
            },
            error: function() {
                alert('An error occurred while removing the item from cart.');
            }
        });
    }

    // Function to reload cart items fragment
    function reloadCartItems() {
        $('#cart-items-container').load('cart #cart-items-container > *', function() {
            // Reattach event handlers after content is loaded
            attachEventHandlers();
        });
    }

    // Function to attach event handlers to cart buttons
    function attachEventHandlers() {
        // Increase quantity button
        $('.cart-update-btn').off('click').on('click', function() {
            const productId = $(this).data('product-id');
            const variantId = $(this).data('variant-id');
            const action = $(this).data('action');
            updateCartItem(productId, variantId, action);
        });

        // Remove item button
        $('.cart-remove-btn').off('click').on('click', function() {
            const productId = $(this).data('product-id');
            const variantId = $(this).data('variant-id');
            removeCartItem(productId, variantId);
        });
    }

    // Function to update cart summary
    function updateCartSummary(cartSubtotal) {
        // Update subtotal
        $('.col-lg-4 .border-bottom .d-flex:first-child h6:last-child').text(cartSubtotal + ' đ');

        // Get shipping cost
        const shippingCostText = $('.col-lg-4 .border-bottom .d-flex:last-child h6:last-child').text();
        const shippingCost = parseFloat(shippingCostText.replace(' đ', ''));

        // Calculate and update total
        const subtotal = parseFloat(cartSubtotal);
        const total = subtotal + shippingCost;
        $('.col-lg-4 .pt-2 .d-flex h5:last-child').text(total + ' đ');
    }

    // Function to update cart count in header
    function updateCartCount(count) {
        // Update cart count in mobile view
        $('.d-inline-flex.align-items-center.d-block.d-lg-none .fa-shopping-cart').next('span').text(count);

        // Update cart count in desktop view
        $('.d-flex.align-items-center .fa-shopping-cart').next('span').text(count);
    }

    // Initial attachment of event handlers
    attachEventHandlers();
});
