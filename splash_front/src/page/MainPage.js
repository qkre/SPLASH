import axios from "axios";
import { useEffect, useState } from "react";
import TotalScoreTable from "../elements/TotalScoreTable";
import WeekScoreTable from "../elements/WeekScoreTable";
import { Link } from "react-router-dom";

export default function MainPage() {
  const [date, setDate] = useState("N주차");
  const [searchMode, setSearchMode] = useState("totalMode");
  const [scores, setScores] = useState([]);

  useEffect(() => {
    if (searchMode === "weekMode") {
      axios
        .get("/api/score/week/" + date)
        .then((res) => setScores(res.data))
        .catch((err) => console.log(err));
    } else {
      axios
        .get("/api/score/all")
        .then((res) => setScores(res.data))
        .catch((err) => console.log(err));
    }
  }, [date, searchMode]);

  return (
    <div>
      <p>안녕하세요. 메인 페이지 입니다.</p>
      <Link to={"/upload"}>업로드 페이지로 이동</Link>
      <form
        id="searchForm"
        onSubmit={(event) => {
          event.preventDefault();
          console.log(document.querySelector("#targetWeek").value);
          console.log(
            document.querySelector("input[name='searchMode']:checked").value
          );
          setSearchMode(
            document.querySelector("input[name='searchMode']:checked").value
          );
          setDate(document.querySelector("#targetWeek").value);
        }}
      >
        <label>
          <input type="radio" name="searchMode" value="weekMode" />
          주차 검색
        </label>
        <label>
          <input type="radio" name="searchMode" value="totalMode" />
          전체 검색
        </label>

        <select id="targetWeek">
          <option value="1주차">1주차</option>
          <option value="2주차">2주차</option>
          <option value="3주차">3주차</option>
          <option value="4주차">4주차</option>
          <option value="5주차">5주차</option>
          <option value="6주차">6주차</option>
          <option value="7주차">7주차</option>
          <option value="8주차">8주차</option>
          <option value="9주차">9주차</option>
          <option value="10주차">10주차</option>
          <option value="11주차">11주차</option>
          <option value="12주차">12주차</option>
          <option value="13주차">13주차</option>
          <option value="14주차">14주차</option>
        </select>
        <button type="submit">검색</button>
      </form>
      <div>
        <form>
          {searchMode === "totalMode" ? (
            <TotalScoreTable scores={scores} />
          ) : (
            <WeekScoreTable scores={scores} />
          )}
        </form>
      </div>
    </div>
  );
}
