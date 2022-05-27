# Run the application
To run the application, MkTextGeneratorApplication is the main Spring boot class which starts the application.

## Run Project through Maven
Run below command in terminal.

`./mvnw spring-boot:run`

This will publish rest api  at `localhost:8080/text` and also there is one Postman file which you will find at root directory. Basically this POST service accept form data.
{
  file: "",
  prefix:"",
  suffix: "",
  length: ""
}

file parameter supports multiple format such as PDF, DOCX etc.

length parameter is not functional yet.

# Overall Architecture
![MKController](https://user-images.githubusercontent.com/6595915/170620448-65c7a507-c859-4ff1-aa5d-78884dff37eb.png)

## Word Freq Table 
It's a dictionary for what word will comes next?

Foe example if input file has text "Hello World! I am awesome."

then Word Freq Table looks like this.

{Hello=[World!], World!=[Programming], Programming=[is], is=[awesome.]}

## Item Reader
Item reader is an interface to read file line by line. Currently there are two implementation available -- one is FlatFileReader which can read nomal text file and another one is ApacheTikeFileReader which supports multiple file format such as PDF, docx etc.

## ItemProcessor 
Create Word Freq Table using ItemReader.

## TextGenerator
TextGenerator is an interface and TextGeneratorImpl is the implementation class. It calls ItemProcessor to get WordFreqTable. Then use that table to generate text.
Basically it performs the Breadth first search through dictionary to find a path between prefix to suffix.


