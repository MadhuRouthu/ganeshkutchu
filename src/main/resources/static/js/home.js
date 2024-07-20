 // Function to show footer content with transition when scrolling to the footer section
        function showFooterContentWithTransition() {
            var footerContainer = document.querySelector('.footer-container');
            var footerOffsetTop = document.querySelector('footer').offsetTop;
            var windowHeight = window.innerHeight;
 
            // Calculate the threshold to trigger the transition
            var threshold = footerOffsetTop - windowHeight + 100; // Adjust threshold as needed
 
            // Function to handle scroll event
            function handleScroll() {
                if (window.scrollY > threshold) {
                    // Add the transition class to show the footer content
                    footerContainer.classList.add('transition');
                    // Remove the scroll event listener to prevent unnecessary calculations
                    window.removeEventListener('scroll', handleScroll);
                }
            }
 
            // Attach the scroll event listener
            window.addEventListener('scroll', handleScroll);
        }
 
        // Call the function when the window is loaded
        window.onload = showFooterContentWithTransition;