# ToastMasters Table Topic API
As a member of ToastMasters we are periodically asked to answer a “Table Topic” question. Table Topic questions impromptu questions that are asked to various people. The purpose of table topics is to have members "think on their feet" and speak for one to two minutes. This can help people prepare for unexpected questions that may be asked during job interviews, news interviews, and business meetings. These questions normally last one to two minutes.


## Prerequisites
Internet Connection and a web browser.


## Usage
### Accessing
This API can be accessed by going to “https://toastmasterstabletopicsapi.herokuapp.com/randomTopic” and entering the required information as parameters. The only required parameter is “count”. You must specify how many topics you would like to retrieve. Doing this will retrieve the requested number of random table topics.
### Parameters
#### Below are the parameters that can be used (remember, they ARE case sensitive):
count – (Mandatory) This is how many Table Topics you would like the API to retrieve.
category1 – The first category of topics you would like to search. You can add this parameter multiple times. This will combine those categories to further define your search (ex. Holiday & Time = Gathering topics that fall into the categories of BOTH Holiday and Time).
category2 – The second category of topics you would like to search. This will allow to you complete another search separate from category1. This search will add its results to category1. Like caregory1, you can add this parameter multiple times. This will also combine those categories to further define your search (ex. Summer & Food = Gathering topics that fall into the categories of both Summer and Food. It will add these results to the results retrieved from the “category1” search).
category3 – The third category of topics you would like to search. This will allow to you complete a third search separate from category1 and category2. This search will add its results to both category1 and category2. Like the previous categories, you can add this parameter multiple times. This will also combine those categories to further define your search.
### Categories
#### Below are the valid categories this API recognizes:
Cold        Warm        Hot        Religion        Home        Family        Friends        Love        Hate        Political        Time        Literature        Pop Culture        Emotions        Current Events        How-To        Music        History        Education        Health        Money        Self        Celebration        Food        Holiday        Vacation        Finish/Fill in the blank        Indoor        Outdoor        Task        Opinion        Controversy        Hobby        Mental Health        Confidence        Nature        Vintage        Religion        Fiction        Non-Fiction        Law        Animals        People        Criticism        Career        Sports        Recreation        Children        Adults        Happy        Sad        Art        Technology        Favorite        Motivation/Inspiration        Winter        Fall        Spring        Summer        Memories        
### URL Examples
#### Below are examples of how the URL should be formatted:
https://toastmasterstabletopicsapi.herokuapp.com/randomTopic?count=9&category1=Self
https://toastmasterstabletopicsapi.herokuapp.com/randomTopic?count=5&category1=Summer&category1=Vacation
https://toastmasterstabletopicsapi.herokuapp.com/randomTopic?count=20&category1=Food&category1=Summer&category2=Time
https://toastmasterstabletopicsapi.herokuapp.com/randomTopic?count=15&category1=Opinion&category2=Happy&category3=Literature


## Contact Information for the Programmer
### Name: Akeem Adkins
### E-Mail: adkinsakeem@gmail.com
### GitHub: https://github.com/adkinsakeem/


## More Information
#### For more information on ToastMasters, visit their site at [https://toastmasters.csod.com/](https://toastmasters.csod.com/)