# Craftorio

A Minecraft mod that adds a fully configurable research system inspired by Factorio. Designed to give modpack developers a way to slow player progression and create meaningful advancement between game stages — establishing clear boundaries between gameplay phases and shifting focus toward process automation and factory building.

---

## Features

### Blocks & Machines
- [ ] **Drafting Table** — Craft Blueprints using dye, paper, and a bit of XP
- [ ] **Primitive Laboratory** — Early-game research station that runs on Blueprints alone
- [ ] **Laboratory** — Advanced 4×4×4 multiblock that accepts all science pack types

### Research System
- [ ] Research tree with prerequisites — unlock technologies in order, just like Factorio
- [ ] Factorio-style research tree GUI to browse and track your progress
- [ ] Locked content is hidden from NEI until researched
- [ ] Locked items and blocks cannot be crafted until the relevant research is complete
- [ ] Organic discovery — if you find a locked item in the world, you can still use it
- [ ] Full localization support for research names

### Science Packs
- [ ] 12 science pack types for modpack authors to gate progression with (Automation, Logistic, Military, Chemical, Production, Utility, Space, Metallurgic, Electromagnetic, Agricultural, Cryogenic, Promethium)
- [ ] Packs have no built-in recipes — modpack authors decide how they're obtained

### Multiplayer
- [ ] Team-shared research progress via ServerUtilities — everyone on the same team works toward the same unlocks

### Modpack Author Configuration
- [ ] JSON-based research definitions — specify name, prerequisites, required inputs, unlocks, and duration
- [ ] Adjustable research speed for both the Primitive Laboratory and the Laboratory
- [ ] Optional energy consumption (RF or HE) for either or both machines
- [ ] Optional pollution output for the Laboratory (requires HBM's Nuclear Tech Mod)

---

## Compatibility
- ServerUtilities (optional — team research sync)
- HBM's Nuclear Tech Mod (optional — pollution)
- RF & HE energy systems
