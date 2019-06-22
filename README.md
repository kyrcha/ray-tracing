This README file contains information related to compiling, running and
using a ray tracing program, developed for the requirements of the first
project of CS510, Image Computation course, of the Computer Science 
Department at CSU.

AUTHOR: Kyriakos Chatzidimitriou

Commands appear in double quotes.

# 1. Compiling:

- Log in on a Dept. Linux machine such as 'antero' or 'wetterhorn'
- Please note that the above machines are 64 bit, so in case the program
is executed on a 32 bit Linux machine, recompilation is needed
- Put the zip file in a temporary directory
- Unzip the compilation in the directory ("unzip kyriakosRT.zip")
- Type "javac RayTracing.java" or use the compile script by 
typing "./compile"

# 2. Running:

- To render the first scene type "./first" or type
"/usr/local/java/bin/java RayTracing first.in first"
- To render the second scene type "./second" or type
"/usr/local/java/bin/java RayTracing second.in second"
- To render the third scene type "./third" or type
"/usr/local/java/bin/java RayTracing third.in third"
- In general the first argument is the name of the input file and the second
argument is the name of the output file. Two formats are generated *.ppm and
*.png. So for example in the first scene two images first.ppm and first.png 
will be created.

# Contents
- *.java: Java source files
- README: This file
- first, second, third: Scripts for creating the 1st, 2nd and 3rd required 
images
- first.in, second.in, third.in: Input files  for the 1st, 2nd and 3rd required 
images 
- compile: Script for compiling
- demo1.png, demo2.png, demo3.png: Demo images for the 1st, 2nd and 3rd required 
images.

# Input File Format

    CAMERA
    0.0 0.0 0.0   # View Reference Point
    0.0 1.0 0.0   # Up vector
    0.0 0.0 1.0   # View Plane Normal (points to the scene)
    100 100       # Horizontal and vertical field fo view
    600 600       # Numbers of pixel rows and pixel columns

    AMBIENT
    0.3 0.3 0.3   # R G B components of ambient light

    BACKGROUND
    0.55 0.75 1.0 # R G B components of the background

    LIGHT         # Multiple light source can be created this way
    2.0 10.0 -5.0 # 3D light source coordinates
    1.0 1.0 1.0   # R G B components of a light source

    SPHERE        # Multiple spheres can be created this way
    -3.0 -1.0 9.0 # Center
    3.0           # Radius
    0.2 0.2 0.2   # Diffuse coefficients
    0.8 0.8 0.8   # Specular coefficients
    0.4 0.4 0.4   # Reflection coefficients
    0.0 0.0 0.0   # Refraction coefficients
    1.5           # eta coefficient
    64            # Phong

    TRIANGLE      # Multiple triangles can be created this way
    10.0 -8.0 0.0 # 3D coordinates of vertex 1 (should be in anti-clockwise order)
    5.0 -8.0 0.0  # 3D coordinates of vertex 2
    10.0 -8.0 5.0 # 3D coordinates of vertex 3
    1.0 0.0 0.0   # Diffuse coefficients
    0.2 0.2 0.2   # Specular coefficients
    0.0 0.0 0.0   # Reflection coefficients
    0.0 0.0 0.0   # Refraction coefficients
    1.5           # eta coefficient
    256           # Phong

    MAX_DEPTH
    3             # maximum depth of the recursion

# Final Notes
The third input file (third.in) is really big (7000+ spheres 6,000+ triangles)
so it may take some time to render.

# Examples

![](https://github.com/kyrcha/ray-tracing/blob/master/demo1.png?raw=true)

![](https://github.com/kyrcha/ray-tracing/blob/master/demo2.png)

![](https://github.com/kyrcha/ray-tracing/blob/master/demo3.png)
