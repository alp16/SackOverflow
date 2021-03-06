package is.hi.hpv501.sack.SackOverflow.Controllers;

        import is.hi.hpv501.sack.SackOverflow.Entities.Team;
        import is.hi.hpv501.sack.SackOverflow.Services.TeamService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;

        import javax.validation.Valid;

@Controller
public class HomeController {

    private TeamService teamService;
    @Autowired
    public HomeController(TeamService teamService){this.teamService = teamService;}

    @RequestMapping("/")
    public String Home(Model model){
        model.addAttribute("teams", teamService.findAll());
        return "Velkominn";
    }

    @RequestMapping(value ="/addteam", method = RequestMethod.POST)
    public String addTeam(@Valid Team team, BindingResult result, Model model){
        if(result.hasErrors()){
            return "add-team";
        }
        teamService.save(team);
        model.addAttribute("teams", teamService.findAll());
        return "Velkominn";
    }

    @RequestMapping(value="/addteam", method = RequestMethod.GET)
    public String addTeamForm(Team team){
        return "add-team";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteTeam(@PathVariable("id") long id, Model model){
        Team team = teamService.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid team ID"));
        teamService.delete(team);
        model.addAttribute("teams", teamService.findAll());
        return "Velkominn";
    }
}
