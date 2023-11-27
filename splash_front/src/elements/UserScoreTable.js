import { useState, useEffect } from "react";

export default function UserScoreTable(props) {
  const { scores } = props;
  const [tableElement, setTableElement] = useState([]);
  useEffect(() => {
    try {
      const scoreList = scores.map((score) =>
        score.map((s, index) => {
          const semester = s.semester;
          const week = s.week;
          const name = s.user.name;
          const rank = s.ranks;
          const firstScore = s.firstScore;
          const secondScore = s.secondScore;
          const thirdScore = s.thirdScore;
          const dayTotalScore = s.dayTotalScore;
          const dayAverage = s.dayAverage.toFixed(2);

          return (
            <tr key={index}>
              <td>{semester}</td>
              <td>{week}주차</td>
              <td>{name}</td>
              <td>{rank}등</td>
              <td>{firstScore}점</td>
              <td>{secondScore}점</td>
              <td>{thirdScore}점</td>
              <td>{dayTotalScore}점</td>
              <td>{dayAverage}점</td>
            </tr>
          );
        })
      );
      setTableElement(scoreList);
    } catch (e) {
      console.error(e);
    }
  }, [scores]);

  return (
    <div>
      <form>
        <table border="1">
          <thead>
            <tr>
              <th>학기</th>
              <th>주차</th>
              <th>이름</th>
              <th>순위</th>
              <th>1 게임</th>
              <th>2 게임</th>
              <th>3 게임</th>
              <th>총 점수</th>
              <th>평균 점수</th>
            </tr>
          </thead>
          <tbody>{tableElement}</tbody>
        </table>
      </form>
    </div>
  );
}
