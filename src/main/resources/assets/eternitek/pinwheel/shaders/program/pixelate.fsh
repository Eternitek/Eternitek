uniform sampler2D DiffuseSampler0;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec2 uv = 16 - fract(texCoord * 32) / texCoord;
    fragColor = texture(DiffuseSampler0, uv);
}