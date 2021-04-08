const BLANK = ".";
let gameStatus = true;

async function reflectBoard() {
  /**
   * batch Piece on Board
   */
  let data;
  try {
    const res = await axios({
      method: 'post',
      url: '/start',
    });
    data = res.data;
    console.log(data);
  } catch (e) {
    console.log(e);
  }

  resetBoard()
  fillBoard()

  eventList()

  function fillBoard() {
    const table = document.getElementById("chessBoard");
    for (let i = 0; i < 8; i++) {
      const newTr = document.createElement("tr");
      for (let j = 0; j < 8; j++) {
        const newTd = document.createElement("td");

        const row = String(8 - i);
        const referenceColumn = 'a'.charCodeAt();
        const column = String.fromCharCode(referenceColumn + j);
        let position = column + row;
        newTd.id = position;
        const pieceName = data[position].teamColor
            + data[position].pieceType;

        if (data[position].pieceType !== BLANK) {
          const piece = document.createElement("img");
          piece.src = "images/" + pieceName + ".png";
          newTd.appendChild(piece);
        }

        newTr.appendChild(newTd);
      }
      table.appendChild(newTr);
    }
  }
}

function resetBoard() {
  const chessBoard = document.getElementById("chessBoard");
  chessBoard.innerHTML = '';
}

function eventList() {
  const table = document.getElementById("chessBoard");
  table.addEventListener("click", selectPiece);
}

function selectPiece(event) {
  if (gameStatus) {
    alert("게임이 이미 종료되었습니다.");
    return;
  }
  const clickPiece = event.target.closest("td");
  const clickedPiece = getClickedPiece();

  if (clickedPiece) {
    clickedPiece.classList.toggle("clicked");
    const sourcePosition = clickedPiece.id;
    const targetPosition = clickPiece.id;

    if (sourcePosition != targetPosition) {
      move(sourcePosition, targetPosition);
      return
    }
    if (sourcePosition == targetPosition) {
      clickedPiece.classList.toggle("clicked");
    }
  }
  clickPiece.classList.toggle("clicked");

}

function getClickedPiece() {
  const tds = document.getElementsByTagName("td");
  for (let i = 0; i < tds.length; i++) {
    if (tds[i].classList.contains("clicked")) {
      return tds[i];
    }
  }
  return null;
}

function modifyScore(whiteScore, blackScore) {
  document.getElementById("whitePoint").innerText = "white Point: "
      + whiteScore;
  document.getElementById("blackPoint").innerText = "black Point: "
      + blackScore;
}

async function move(source, target) {
  /**
   * run move
   * source : String
   * target : String
   */
  let data;
  try {
    const res = await axios({
      method: 'post',
      url: '/move',
      data: {
        source: source,
        target: target
      }
    });
    data = res.data;
  } catch (e) {
    console.log(e);
  }
  if (!data.runningGame) {
    reflectBoard()
    alert("게임이 종료되었습니다. 승자는 " + data.winner + "입니다.");
    gameStatus = false;
    return
  }
  if (!data.isMove) {
    alert("올바른 위치를 입력하여 주세요");
  }

  console.log(data);

  modifyScore(data.whiteScore, data.blackScore)
  reflectBoard()
}

async function reset() {
  try {
    await axios({
      method: 'post',
      url: '/reset',
    });
  } catch (e) {
    console.log(e);
  }
  const defaultScore = 38;
  gameStatus = true;
  alert("로그 정보가 초기화 되었습니다.");
  modifyScore(defaultScore, defaultScore);
  reflectBoard();
}

async function load() {
  alert("run load!");
  try {
    await axios({
      method: 'post',
      url: '/load',
      data: {
        roomName: "room1"
      }
    });
  } catch (e) {
    console.log(e);
  }
  reflectBoard();
}