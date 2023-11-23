import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

export default function UploadPage() {
  const navigate = useNavigate();
  const onSelectFile = async () => {
    const fileSelector = document.querySelector(".fileSelector");
    if (fileSelector.files[0]) {
      const file = fileSelector.files[0];
      const formData = new FormData();
      console.log(file);
      formData.append("file", file);
      try {
        const response = await axios.post("/api/excel/upload", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        console.log(response.data);
        alert("업로드 성공!");
        navigate("/");
      } catch (err) {
        console.error("Upload Failed : ", err);
        alert("업로드 실패..");
      }
    }
  };

  return (
    <div>
      업로드 페이지
      <input
        className="fileSelector"
        type="file"
        name="주간 점수"
        accept=".xls, .xlsx"
      />
      <button onClick={onSelectFile}>업로드 하기</button>
    </div>
  );
}
