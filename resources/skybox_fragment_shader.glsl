#version 140

in vec3 frag_texcoord;

out vec3 color;

uniform samplerCube skybox;

vec3 tonemap(vec3 linearRGB)
{
    float L_white = 0.7;

    float inverseGamma = 1./2.2;
    return pow(linearRGB/L_white, vec3(inverseGamma));
}

void main()
{
	vec3 linear_color = texture(skybox, frag_texcoord).xyz;

	color = tonemap(linear_color);
}
