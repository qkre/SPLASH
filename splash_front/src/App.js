import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import MainPage from "./page/MainPage";
import UploadPage from "./page/UploadPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index="/" element={<MainPage />} />
        <Route path="/upload" element={<UploadPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
