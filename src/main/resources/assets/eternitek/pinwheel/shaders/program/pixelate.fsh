in vec2 texCoord;

out vec4 fragColor;

void main() {
    fragColor = vec4(texCoord.rg, 0, 1);
}