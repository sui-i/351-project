var express = require("express");
var app = express();
var port = 3000;

app.use(express.static("Frontend"));
app.use(express.json());

app.post("/", (request, response) => {
  const { parcel } = request.body;
  console.log(parcel);
  if (!parcel) {
    return response.status(400).send({ status: "failed" }); // send a failed status in case parcel not received
  }
  res.status(200).send({ status: "received" });
});

app.listen(port, () => console.log(`Server has started on port: ${port}`));
