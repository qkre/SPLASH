import { useState } from "react";
import axios from "axios";

export default function UploadPage() {
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
      } catch (err) {
        console.error("Upload Failed : ", err);
      }
    }
  };

  return (
    <div>
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
