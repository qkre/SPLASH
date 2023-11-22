import axios from "axios";
import { useEffect, useState } from "react";

export default function MainPage() {
  const [scores, setScores] = useState();
  const [scoreElement, setScoreElement] = useState();

  useEffect(() => {
    axios
      .get("/api/score/all")
      .then((res) => setScores(res.data))
      .catch((err) => console.log(err));
  }, []);

  useEffect(() => {
    try {
      if (scores !== undefined) {
        const scoreList = scores.map((score) => {
          const userName = score.user.name;
          const date = score.date;
          const firstScore = score.firstScore;
          const secondScore = score.secondScore;
          const thirdScore = score.thirdScore;

          return (
            <div>
              <p>{userName}</p>
              <p>{date}</p>
              <p>{firstScore}</p>
              <p>{secondScore}</p>
              <p>{thirdScore}</p>
            </div>
          );
        });
        setScoreElement(scoreList);
      }
    } catch (err) {
      console.error(err);
    }
  }, [scores]);

  return (
    <div>
      <p>안녕하세요. 메인 페이지 입니다.</p>
      <div>{scoreElement}</div>
    </div>
  );
}
