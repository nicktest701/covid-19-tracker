package country;

import java.text.DecimalFormat;

public class Country {

    String country;
    Integer cases;
    Integer todayCases;
    Integer deaths;
    Integer todayDeaths;
    Integer recovered;
    Integer active;
    Integer critical;
    Integer casesPerOneMillion;
    Integer deathsPerOneMillion;
    Integer totalTests;
    Integer testsPerOneMillion;

    public Country(String country, Integer cases, Integer todayCases, Integer deaths, Integer todayDeaths, Integer recovered, Integer active, Integer critical, Integer casesPerOneMillion, Integer deathsPerOneMillion, Integer totalTests, Integer testsPerOneMillion) {
        this.country = country;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.active = active;
        this.critical = critical;
        this.casesPerOneMillion = casesPerOneMillion;
        this.deathsPerOneMillion = deathsPerOneMillion;
        this.totalTests = totalTests;
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCases() {
        if (cases == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(cases);
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        if (todayCases == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(todayCases);
    }

    public void setTodayCases(Integer todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        if (deaths == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(deaths);
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        if (todayDeaths == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(todayDeaths);
    }

    public void setTodayDeaths(Integer todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        if (recovered == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(recovered);
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        if (active == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(active);
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCritical() {
        if (critical == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(critical);
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public String getCasesPerOneMillion() {
        if (casesPerOneMillion == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(casesPerOneMillion);
    }

    public void setCasesPerOneMillion(Integer casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public String getDeathsPerOneMillion() {
        if (deathsPerOneMillion == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(deathsPerOneMillion);
    }

    public void setDeathsPerOneMillion(Integer deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public String getTotalTests() {
        if (totalTests == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(totalTests);
    }

    public void setTotalTests(Integer totalTests) {
        this.totalTests = totalTests;
    }

    public String getTestsPerOneMillion() {
        if (testsPerOneMillion == null) {
            return "---";
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(testsPerOneMillion);
    }

    public void setTestsPerOneMillion(Integer testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

}
