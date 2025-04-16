document.addEventListener('DOMContentLoaded', function () {
    const variantSelect = document.getElementById('variant-select');
    const priceDisplay = document.getElementById('product-price');

    function formatCurrency(amount) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(amount);
    }

    variantSelect.addEventListener('change', function () {
        const selectedOption = variantSelect.options[variantSelect.selectedIndex];
        const price = parseFloat(selectedOption.getAttribute('data-price'));
        priceDisplay.textContent = formatCurrency(price);
    });

    // Set initial price based on the first variant
    const initialOption = variantSelect.options[variantSelect.selectedIndex];
    const initialPrice = parseFloat(initialOption.getAttribute('data-price'));
    priceDisplay.textContent = formatCurrency(initialPrice);
});
