import { useState, useEffect } from "react";

export default function TotalScoreTable(props) {
  const { scores } = props;
  const [date, setDate] = useState();
  const [tableElement, setTableElement] = useState([]);
  useEffect(() => {
    try {
      const scoreList = scores.map((score, index) => {
        const userName = score.user.name;
        const date = score.date;
        const totalScore = score.totalScore;
        const played = score.played;
        const average = score.average.toFixed(2);

        setDate(date);

        return (
          <tr key={index}>
            <td>{index + 1}등</td>
            <td>{played}게임</td>
            <td>{userName}</td>
            <td>{totalScore}점</td>
            <td>{average}점</td>
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
      <form>
        <table border="1">
          <thead>
            <tr>
              <th>순위</th>
              <th>게임 수</th>
              <th>이름</th>
              <th>총 점</th>
              <th>평균 점수</th>
            </tr>
          </thead>
          <tbody>{tableElement}</tbody>
        </table>
      </form>
    </div>
  );
}
