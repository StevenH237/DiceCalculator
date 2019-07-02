# Explanation of object types

* **number**: A single number, or other single-value objects like a Die or Coin. If you use a list here, the sum of that list's items will be used instead.
* **list**: A list of values. If you use a number here, a list containing only that number will be used instead.
* **comparison**: A comparison operator (`>`, `<`, `<=`, `>=`, `=`, `!=`) followed by a number.
* **operation**: An operation consists of the operator and the arguments to either side of it. Some operators run on operations directly rather than the values those operations return.