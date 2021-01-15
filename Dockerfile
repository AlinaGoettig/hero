FROM hseeberger/scala-sbt
WORKDIR /hero
ADD . /hero
CMD sbt run