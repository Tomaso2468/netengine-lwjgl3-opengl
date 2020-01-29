#version 330
out vec4 FragColor;
in vec2 TexCoord;

void main()
{
    FragColor = vec4(TexCoord.r + 0.5, TexCoord.g + 0.5, 0.5, 1);
} 