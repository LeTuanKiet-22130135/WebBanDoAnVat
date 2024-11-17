document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('price-filter-form');
    const checkboxes = form.querySelectorAll('input[type="checkbox"]');
    const productContainer = document.getElementById('product-container');
    
    // Capture the existing query parameter from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const existingQuery = urlParams.get('query') || ''; // Get current query value, if any

    function sendFilterRequest() {
        console.log('Checkbox change detected, sending request');

        // Collect selected price ranges
        let selectedRanges = [];
        checkboxes.forEach(cb => {
            if (cb.checked && cb.value !== 'all') {
                selectedRanges.push(cb.value);
            }
        });

        // Handle case where no filters are selected or "all" is selected
        if (selectedRanges.length === 0 || form.querySelector('#price-all').checked) {
            selectedRanges = ['all'];
        }

        // Create URL parameters for the request
        const params = new URLSearchParams();
        if (selectedRanges.length > 0) {
            params.append('priceRange', selectedRanges.join(','));
        }

        // Include the existing search query if present
        if (existingQuery.trim() !== '') {
            params.append('query', existingQuery);
        }

        // Construct the URL, avoiding appending an empty query string
        let requestUrl = 'shop';
        if (params.toString()) {
            requestUrl += '?' + params.toString();
        }

        // Log the full URL being fetched
        console.log(`Fetching data from: ${requestUrl}`);

        // Make AJAX request
        fetch(requestUrl, {
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Network response was not ok: ${response.statusText}`);
            }
            return response.text();
        })
        .then(html => {
            productContainer.innerHTML = html;
        })
        .catch(error => console.error('Error fetching filtered products:', error));
    }

    // Attach event listeners to checkboxes
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            sendFilterRequest();
        });
    });
});