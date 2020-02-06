#version 330

in vec3 FragPos;

out vec4 FragColor;

void main()
{
	FragColor = vec4(abs(FragPos / vec3(10)) + vec3(0.2), 1);
} 