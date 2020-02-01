#version 330
out vec4 FragColor;
in vec2 TexCoord;
in float textureForFragment;

uniform sampler2D texture1;
uniform sampler2D texture2;

void main()
{
	if (textureForFragment < 0.5) {
		FragColor = texture(texture2, TexCoord);
	} else {
		FragColor = texture(texture1, TexCoord);
    }
} 