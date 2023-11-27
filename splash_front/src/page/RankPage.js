import axios from "axios";
import { useEffect, useState } from "react";
import TotalScoreTable from "../elements/TotalScoreTable";
import WeekScoreTable from "../elements/WeekScoreTable";
import { Link } from "react-router-dom";
import UserScoreTable from "../elements/UserScoreTable";

export default function RankPage() {
  const [targetDate, setTargetDate] = useState({});
  const [semester, setSemester] = useState([]);
  const [weeks, setWeeks] = useState([]);
  const [searchMode, setSearchMode] = useState("totalMode");
  const [userName, setUserName] = useState("");
  const [scores, setScores] = useState([]);
  const [tableElement, setTableElement] = useState();

  useEffect(() => {
    axios
      .get("/api/score/dates")
      .then((res) => {
        const uniqueSemester = [
          ...new Set(res.data.map((date) => date.semester)),
        ];

        const semesterList = uniqueSemester.map((semester) => {
          return (
            <option key={semester} value={semester}>
              {semester}
            </option>
          );
        });

        setSemester(semesterList);

        const weekList = res.data.map((date) => {
          return (
            <option key={date.semester + date.week} value={date.week}>
              {date.week}주차
            </option>
          );
        });
        setWeeks(weekList);
      })
      .catch((err) => console.error(err));
  }, [targetDate]);

  useEffect(() => {
    try {
      if (searchMode === "weekMode") {
        console.log(targetDate);
        axios
          .get(
            `/api/score/week/${encodeURIComponent(targetDate.semester)}/${
              targetDate.week
            }`
          )
          .then((res) => setScores(res.data))
          .catch((err) => console.log(err));
      } else if (searchMode === "totalMode") {
        axios
          .get(`/api/score/${targetDate.semester}/all`)
          .then((res) => setScores(res.data))
          .catch((err) => console.log(err));
      } else if (searchMode === "userMode") {
        console.log(userName);
        axios
          .post("/api/score/last", userName, {
            headers: {
              "Content-Type": "text/plain",
            },
          })
          .then((res) => {
            console.log(res);
            setScores(res.data);
          })
          .catch((err) => console.log(err));
      }
    } catch (e) {
      console.error(e);
    }
  }, [targetDate, searchMode]);

  useEffect(() => {
    if (searchMode === "weekMode") {
      setTableElement(<WeekScoreTable scores={scores} />);
    } else if (searchMode === "totalMode") {
      setTableElement(<TotalScoreTable scores={scores} />);
    } else if (searchMode === "userMode") {
      setTableElement(<UserScoreTable scores={scores} />);
      setSearchMode("");
    }
  }, [scores]);

  const handleSearchWeek = () => {
    const target = {
      semester: document.querySelector("#targetSemester").value,
      week: document.querySelector("#targetWeek").value,
    };
    setTargetDate(target);
    setSearchMode("weekMode");
  };

  const handleSearchSemester = () => {
    const target = {
      semester: document.querySelector("#targetSemester").value,
      week: document.querySelector("#targetWeek").value,
    };
    setTargetDate(target);
    setSearchMode("totalMode");
  };

  const handleSearchUser = (e) => {
    setUserName(document.querySelector("#targetUserName").value);
    setSearchMode("userMode");
  };

  return (
    <div>
      <Link to={"/"}>SPLASH</Link>
      <section className="searchSection">
        <div>
          <select id="targetSemester" defaultValue={"2023년 2학기"}>
            {semester}
          </select>
          <select id="targetWeek" defaultValue="1">
            {weeks}
          </select>
          <button onClick={handleSearchWeek}>검색</button>
          <button id="totalMode" onClick={handleSearchSemester}>
            전체 검색
          </button>
        </div>
        <div>
          <input
            id="targetUserName"
            placeholder="회원 이름을 입력해주세요..."
            type="text"
            onKeyUp={(e) => {
              if (e.key === "Enter") {
                handleSearchUser();
              }
            }}
          ></input>
          <button type="submit" onClick={handleSearchUser}>
            검색
          </button>
        </div>
      </section>

      <div>
        <form>{tableElement}</form>
      </div>

      <Link to={"/upload"}>업로드 페이지로 이동</Link>
    </div>
  );
}
