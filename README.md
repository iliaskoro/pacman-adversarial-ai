# Pac-Man Adversarial AI – Minimax Search & Heuristic Evaluation

This repository implements an **adversarial AI agent for the Pacman game**, focusing on **decision-making under competition** between Pacman and multiple ghosts.

The project includes:
- A **Minimax-based Pacman agent** with heuristic evaluation
- **Greedy ghost agents** that pursue Pacman while avoiding collisions
- A clean separation between **AI logic** and an **external game engine**

The repository is intentionally kept **minimal and focused**, containing only the source code authored by the developer and visual material necessary to demonstrate gameplay behavior.

---

## Project Structure

```
/
├── src/
│   └── gr/auth/ee/dsproject/
│       ├── node/
│       │   └── Node.java
│       └── pacman/
│           └── Creature.java
│
├── images/
│   ├── pacman.gif
│   └── ghosts.gif
│
└── README.md
```

---

## AI Design Overview

### Pacman Agent

Pacman’s behavior is implemented using a **depth-limited Minimax search**:

- Pacman acts as the **maximizing player**
- Ghosts collectively act as the **minimizing opponent**
- The search tree alternates between Pacman and ghost turns
- Decisions are based on heuristic evaluation of future game states

The evaluation function considers:
- Manhattan distance from ghosts (risk avoidance)
- Distance to remaining flags (objective completion)
- Captured vs non-captured flags

### Ghost Agents

![Ghosts](images/ghosts.gif)

Ghosts follow a **greedy pursuit strategy**:

- Each ghost independently minimizes its distance to Pacman
- Wall collisions and illegal moves are avoided
- Ghost-to-ghost collisions (same cell or position swap) are prevented

This design provides a strong adversarial environment without introducing unnecessary complexity.

## External Game Engine

This repository **does not include** the Pacman game engine.

The AI agents are designed to run on top of an **external Pacman engine**, which provides:
- Maze generation
- Game rules and state updates
- Rendering and simulation control

This separation ensures that:
- The repository contains **only original work**
- The AI logic remains reusable and engine-agnostic

## How to Run

To run the project:

1. Obtain a compatible Pacman game engine (`pacman.jar`)
2. Place it locally (not included in this repository)
3. Import the `src/` directory into an IDE such as Eclipse
4. Run the game engine and select the provided AI agent

## Technologies Used

- Java
- Minimax Search
- Heuristic Evaluation Functions
- Adversarial Game AI
- Object-Oriented Design

## Notes

- Compiled binaries and external dependencies are intentionally excluded.
- The repository focuses solely on **AI logic and behavior**.
- Visuals are provided only to demonstrate gameplay outcomes.

## Author

Ilias Korompilis

## License

Educational and research use.
