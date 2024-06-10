    function updateCategoryOptions() {
    let categorySelect = document.getElementById("categorySelect");
    let categoryDetailSelect = document.getElementById("categoryDetailSelect");

    // 기존 옵션들을 모두 제거합니다
    categoryDetailSelect.innerHTML = "";

    for(let i=0; i<productDTO.pcdList.length; i++){

        if(productDTO.pcdList[i].productCategoryId == categorySelect.value){
            let option = document.createElement('option');
            option.text = productDTO.pcdList[i].name;
            option.value = productDTO.pcdList[i].id;
            categoryDetailSelect.add(option);
        }
    }
}



document.getElementById('product-name-input').addEventListener('input',(e)=>{
    const nameLength = e.target.value.length;
    if(nameLength > 25){
        e.target.value = e.target.value.slice(0,25);
        return;
    }
    document.getElementById('product-name-length').innerText = nameLength;

});

    document.getElementById('product-description-input').addEventListener('input',(e)=>{
        const nameLength = e.target.value.length;
        if(nameLength > 45){
            e.target.value = e.target.value.slice(0,45);
            return;
        }
        document.getElementById('product-description-length').innerText = nameLength;
    });


    //파일 validation, 파일 뿌려주기
    document.getElementById('file').addEventListener('change', (e) => {
        const maxFiles = 3;
        const fileList = e.target.files;
        const output = document.getElementById('fileList');
        output.innerHTML = '';

        if (fileList.length > maxFiles) {
            alert(`최대 ${maxFiles}개의 파일만 업로드할 수 있습니다`);
            document.getElementById('file').value = '';
        } else {
            for (let i = 0; i < fileList.length; i++) {
                const file = fileList[i];
                if (file.type.startsWith('image/')) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const img = document.createElement('img');
                        img.src = e.target.result;
                        img.style.width = '124px';
                        img.style.height = 'auto';
                        img.style.marginRight = '10px';
                        img.style.borderRadius = '5px';
                        output.appendChild(img);
                    };
                    reader.readAsDataURL(file);
                } else {
                    alert('이미지 파일만 가능합니다.');
                    document.getElementById('file').value = '';
                    output.innerHTML = '';
                    break;
                }
            }
        }
    });

    //파일 validation, 파일 뿌려주기
    document.getElementById('files').addEventListener('change', (e) => {
        const maxFiles = 3;
        const fileList = e.target.files;
        const output = document.getElementById('filesList');
        output.innerHTML = '';

        if (fileList.length > maxFiles) {
            alert(`최대 ${maxFiles}개의 파일만 업로드할 수 있습니다`);
            document.getElementById('files').value = '';
        } else {
            for (let i = 0; i < fileList.length; i++) {
                const file = fileList[i];
                if (file.type.startsWith('image/')) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const img = document.createElement('img');
                        img.src = e.target.result;
                        img.style.width = '111px';
                        img.style.height = '134px';
                        img.style.marginRight = '10px';
                        img.style.borderRadius = '5px';
                        output.appendChild(img);
                    };
                    reader.readAsDataURL(file);
                } else {
                    alert('이미지 파일만 가능합니다.');
                    document.getElementById('files').value = '';
                    output.innerHTML = '';
                    break;
                }
            }
        }
    });

    document.getElementById('cancel-button').addEventListener('click',()=>{
        location
    });

