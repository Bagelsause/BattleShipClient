# ChangeLog from PA03 to PA04

*changes are marked with a bullet-point and explained below*

<ul>
    <li>Updated Driver to support command-line args.</li>
    <p>This was done to separate the threads of execution between the manual game playing and the server-based game playing</p>
    <li>Added takeRandomShot() to AbstractPlayer</li>
    <p>This was done to further abstract the "random shot" functionality between the RandomPlayer and the AiPlayer.</p>
    <li>Added previouslyShot field to AbstractPlayer</li>
    <p>This was done as consequence of abstracting takeRandomShot(), as it uses a list of coordinates to ensure it doesn't shoot in the same place twice.</p>
    <li>Added @JsonProperty and @JsonCreator to Ship and Coord.</li>
    <p>This was done to make sure that Jackson annotation could be done automatically and easily for a List of Ship and a List of Coord.</p>
</ul>