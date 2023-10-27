const board = document.getElementById("board");
const gridSize = 10;
const ships = [
  { name: "Aircraft Carrier", length: 5, hits: 0, position: [] },
  { name: "Battleship", length: 4, hits: 0, position: [] }
];

function createBoard() {
  board.innerHTML = "";

  for (let i = 0; i < gridSize; i++) {
    for (let j = 0; j < gridSize; j++) {
      const cell = document.createElement("div");
      cell.className = "cell";
      cell.dataset.row = i;
      cell.dataset.col = j;
      cell.addEventListener("click", handleCellClick);
      board.appendChild(cell);
    }
  }
}

function placeShips() {
  ships.forEach(ship => {
    let isShipPlaced = false;

    while (!isShipPlaced) {
      const isVertical = Math.random() < 0.5;
      const row = Math.floor(Math.random() * gridSize);
      const col = Math.floor(Math.random() * gridSize);

      if (isVertical) {
        if (row + ship.length <= gridSize) {
          let canPlaceShip = true;
          const positions = [];

          for (let i = 0; i < ship.length; i++) {
            const cell = document.querySelector(
              `[data-row="${row + i}"][data-col="${col}"]`
            );
            if (cell.classList.contains("ship")) {
              canPlaceShip = false;
              break;
            }
            positions.push([row + i, col]);
          }

          if (canPlaceShip) {
            positions.forEach(pos => {
              const cell = document.querySelector(
                `[data-row="${pos[0]}"][data-col="${pos[1]}"]`
              );
              cell.classList.add("ship", ship.name.split(" ").join(""));
            });
            ship.position = positions;
            isShipPlaced = true;
          }
        }
      } else {
        if (col + ship.length <= gridSize) {
          let canPlaceShip = true;
          const positions = [];

          for (let i = 0; i < ship.length; i++) {
            const cell = document.querySelector(
              `[data-row="${row}"][data-col="${col + i}"]`
            );
            if (cell.classList.contains("ship")) {
              canPlaceShip = false;
              break;
            }
            positions.push([row, col + i]);
          }

          if (canPlaceShip) {
            positions.forEach(pos => {
              const cell = document.querySelector(
                `[data-row="${pos[0]}"][data-col="${pos[1]}"]`
              );
              cell.classList.add("ship", ship.name.split(" ").join(""));
            });
            ship.position = positions;
            isShipPlaced = true;
          }
        }
      }
    }
  });
}

function handleCellClick(event) {
  const cell = event.target;
  if (cell.classList.contains("ship")) {
    cell.classList.add("hit");
    cell.removeEventListener("click", handleCellClick);

    ships.forEach(ship => {
      if (cell.classList.contains(ship.name.split(" ").join(""))) {
        ship.hits++;
        if (ship.hits === ship.length) {
          ship.length = 0;
        }
      }
    });

    const allShipsSunk = ships.every(ship => ship.length === 0);
    if (allShipsSunk) {
      alert("¡Todos los barcos han sido hundidos! ¡Ganaste!");
      resetGame(); // Reiniciar el juego
    }
  } else {
    cell.classList.add("miss");
  }
}

function resetGame() {
  ships.forEach(ship => {
    ship.hits = 0;
    ship.position = [];
  });
  createBoard();
  placeShips();
  alert("Juego reiniciado. Intenta hundir la flota.");
}

resetGame();
