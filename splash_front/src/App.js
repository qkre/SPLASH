import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import MainPage from "./page/MainPage";
import UploadPage from "./page/UploadPage";
import RankPage from "./page/RankPage";
import LottoPage from "./page/LottoPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index="/" element={<MainPage />} />
        <Route path="/upload" element={<UploadPage />} />
        <Route path="/rank" element={<RankPage />} />
        <Route path="/lotto" element={<LottoPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
