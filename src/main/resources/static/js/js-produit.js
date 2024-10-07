
document.getElementById("btn-burger").addEventListener("click",function(){
 
  document.getElementById("list-burger").classList.toggle("show");
})


// document.getElementById("btn-burger").addEventListener("click",function(event){
//   event.preventDefault();
//   console.log("fonction marche");
  
//   document.getElementById("list-burger").classList.toggle("show");
// })

document.getElementById("fleche-the").addEventListener("click",function(){
    document.getElementById("item-content-thé").classList.toggle("show");
})

document.getElementById("fleche-accesoires").addEventListener("click",function(){
    document.getElementById("item-content-accesoires").classList.toggle("show");
})



let slideIndex = 1;
    showSlides(slideIndex);
    
    function plusSlides(n) {
      showSlides(slideIndex += n);
    }
    
    function currentSlide(n) {
      showSlides(slideIndex = n);
    }
    
    function showSlides(n) {
      let i;
      let slides = document.getElementsByClassName("mySlides");
      let dots = document.getElementsByClassName("dot");
      if (n > slides.length) {slideIndex = 1}    
      if (n < 1) {slideIndex = slides.length}
      for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";  
      }
      for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
      }
      slides[slideIndex-1].style.display = "block";  
      dots[slideIndex-1].className += " active";
    }