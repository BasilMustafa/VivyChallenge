package com.bailm.vivychallenge.util

object TimeUtilsParser{
    var daysText = HashMap<String,String>();
    init {
        daysText["1"] = "Monday"
        daysText["2"] = "Tuesday"
        daysText["3"] = "Wednesday"
        daysText["4"] = "Thursday"
        daysText["5"] = "Friday"
        daysText["6"] = "Saturday"
        daysText["0"] = "Sunday"

    }
  fun parsePair(hoursPair:String):String{
      var ends = hoursPair.split("/")
      val day = daysText[ends[0][1].toString()]
      val startHour =  ends[0].substring(3,7)
      val endHours = ends[1].substring(3,7)
      return day?.padEnd(20,' ') + "${startHour.padEnd(10-startHour.length,' ')}  - $endHours"
  }
}