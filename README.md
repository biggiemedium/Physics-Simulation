# Physics Simulation

A little 3D solar system I made with LWJGL. Everything pulls on everything else with gravity (F = GMm/r²)
and you fly around with a free camera watching it happen.

Theres a sun, earth, moon, mars, a meteor and some debris. Planets leave a trail behind them so you can see their orbits,
and each one has its name floating above it.

## Running it

- Java 23 + Maven
- Open it in IntelliJ and run `dev.px.physics.Main`
- Only has the Windows natives in the pom right now, so it wont run on mac/linux without adding those

## Controls

| Key | What it does |
|---|---|
| W A S D | move |
| Space / Left Shift | up / down |
| Up / Down arrow | speed up / slow down the camera |
| Left click + drag | look around |
| O | screenshot (saves to `screenshots/`, HUD included) |

## How its laid out

- `Core` - the Engine, starts the window and loads the scene
- `Rendering` - window, camera, scenes, and the NanoVG stuff for text on screen
- `Objects` - anything in the world, planets extend `PhysicalObject` and implement `Renderable`
- `System` - physics, input, trajectories (the trails) and scaling
- `Util` - vectors and math helpers

## Notes

- Gravity is scaled way up (`MathConstants.GRAVITY = 6674.30`) since real G with these tiny masses barely moves anything
- Physics is O(n²) right now, every object checks every other object. Fine for a few planets, TODO make it faster
- Earth is half the mass of the sun so its pretty chaotic, mars gets flung out after a couple minutes
- Old school OpenGL (glBegin/glEnd), no shaders. The planet colours and lighting are all done per vertex by hand



<img width="1400" height="1000" alt="screenshot_2026-11-02_16-16-00" src="https://github.com/user-attachments/assets/f97be789-7a71-41ad-8140-403f91fada99" />

