import "./App.css";
import MainPage from "./page/MainPage";
import UploadPage from "./page/UploadPage";

function App() {
  return (
    <div>
      {MainPage()}
      {UploadPage()}
    </div>
  );
}

export default App;
