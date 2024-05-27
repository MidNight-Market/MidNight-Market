    function updateCategoryOptions() {
    let categorySelect = document.getElementById("categorySelect");
    let categoryDetailSelect = document.getElementById("categoryDetailSelect");

    // 기존 옵션들을 모두 제거합니다
    categoryDetailSelect.innerHTML = "";

    for(let i=0; i<productCategoryDTO.pcdList.length; i++){

        if(productCategoryDTO.pcdList[i].productCategoryId == categorySelect.value){
            let option = document.createElement('option');
            option.text = productCategoryDTO.pcdList[i].name;
            option.value = productCategoryDTO.pcdList[i].id;
            categoryDetailSelect.add(option);
        }

    }
}
