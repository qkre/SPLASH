import { useState, useEffect } from "react";

export default function WeekScoreTable(props) {
  const { scores } = props;
  const [date, setDate] = useState();
  const [tableElement, setTableElement] = useState([]);
  useEffect(() => {
    try {
      const scoreList = scores.map((score, index) => {
        const userName = score.user.name;
        const date = score.date;
        const firstScore = score.firstScore;
        const secondScore = score.secondScore;
        const thirdScore = score.thirdScore;
        const dayTotalScore = score.dayTotalScore;
        const dayAverage = (dayTotalScore / 3).toFixed(2);
        setDate(date);

        return (
          <tr key={index}>
            <td>{index + 1}등</td>
            <td>{userName}</td>
            <td>{firstScore}점</td>
            <td>{secondScore}점</td>
            <td>{thirdScore}점</td>
            <td>{dayTotalScore}점</td>
            <td>{dayAverage}점</td>
          </tr>
        );
      });
      setTableElement(scoreList);
    } catch (e) {
      console.error(e);
    }
  }, [scores]);

  return (
    <div>
      {date} 점수표
      <form>
        <table border="1">
          <thead>
            <tr>
              <th>순위</th>
              <th>이름</th>
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
