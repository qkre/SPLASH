import axios from "axios";
import { useEffect, useState } from "react";
import TotalScoreTable from "../elements/TotalScoreTable";
import WeekScoreTable from "../elements/WeekScoreTable";
import { Link } from "react-router-dom";
import UserScoreTable from "../elements/UserScoreTable";

export default function MainPage() {
  return (
    <div>
      <p>SPLASH</p>
      <Link to={"/rank"}>스플래쉬 랭킹</Link>
      <Link to={"/lotto"}>스플래쉬 로또</Link>
      <Link to={"/upload"}>업로드 페이지로 이동</Link>
    </div>
  );
}
