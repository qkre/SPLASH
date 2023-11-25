import axios from "axios";
import { useEffect, useState } from "react";
import TotalScoreTable from "../elements/TotalScoreTable";
import WeekScoreTable from "../elements/WeekScoreTable";
import { Link } from "react-router-dom";

export default function MainPage() {
  const [date, setDate] = useState("1");
  const [weeks, setWeeks] = useState([]);
  const [searchMode, setSearchMode] = useState("weekMode");
  const [userName, setUserName] = useState("");
  const [scores, setScores] = useState([]);
  const [tableElement, setTableElement] = useState();

  useEffect(() => {
    axios
      .get("/api/score/weeks")
      .then((res) => {
        const weekList = res.data.map((week) => {
          return (
            <option value={week} key={week}>
              {week}주차
            </option>
          );
        });
        setWeeks(weekList);
      })
      .catch((err) => console.error(err));
  }, []);

  useEffect(() => {
    try {
      if (searchMode === "weekMode") {
        axios
          .get("/api/score/week/" + date)
          .then((res) => setScores(res.data))
          .catch((err) => console.log(err));
      } else if (searchMode === "totalMode") {
        axios
          .get("/api/score/all")
          .then((res) => setScores(res.data))
          .catch((err) => console.log(err));
      } else if (searchMode === "userMode") {
        console.log(userName);
        axios
          .post("/api/score/last", `"${userName}"`, {
            headers: {
              "Content-Type": "application/json",
            },
          })
          .then((res) => console.log(res.data))
          .catch((err) => console.log(err));
      }
    } catch (e) {
      console.error(e);
    }
  }, [date, searchMode]);

  useEffect(() => {
    if (searchMode === "weekMode") {
      setTableElement(<WeekScoreTable scores={scores} />);
    } else if (searchMode === "totalMode") {
      setTableElement(<TotalScoreTable scores={scores} />);
    }
  }, [scores]);

  const handleModeChange = (e) => {
    console.log(e.target.id);
    setSearchMode(e.target.id);
  };

  const handleSearchUser = (e) => {
    console.log(e.target.id);
  };

  return (
    <div>
      <p>안녕하세요. 메인 페이지 입니다.</p>
      <button id="weekMode" onClick={handleModeChange}>
        주차 검색
      </button>
      <button id="totalMode" onClick={handleModeChange}>
        전체 검색
      </button>
      <Link to={"/upload"}>업로드 페이지로 이동</Link>
      <form
        id="userSearchForm"
        onSubmit={(event) => {
          event.preventDefault();
          try {
            setUserName(document.querySelector("#targetUserName").value);

            setSearchMode("userMode");
          } catch (e) {
            console.error(e);
          }
        }}
      >
        <input
          id="targetUserName"
          placeholder="회원 이름을 입력해주세요..."
          type="text"
        ></input>
        <button type="submit" onClick={handleSearchUser}>
          검색
        </button>
      </form>
      <form
        id="searchForm"
        onSubmit={(event) => {
          event.preventDefault();
          try {
            setDate(document.querySelector("#targetWeek").value);
          } catch (e) {
            console.error(e);
          }
        }}
      >
        <select
          id="targetWeek"
          defaultValue="1"
          disabled={searchMode === "weekMode" ? false : true}
        >
          {weeks}
        </select>
        <button type="submit">검색</button>
      </form>
      <div>
        <form>{tableElement}</form>
      </div>
    </div>
  );
}
