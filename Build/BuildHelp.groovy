def BuildHelp()
{
    try
    {
        stage 'Build Help'
        def HelpDir=pwd()

        bat """jekyll build --config "${HelpDir}\\_configOffline.yml" -s "${HelpDir}" -d "${HelpDir}\\spotlightHelp_site" """
        bat """jekyll build --config "${HelpDir}\\_configBalloonHelpOffline.yml" -s "${HelpDir}" -d "${HelpDir}\\spotlightHelp_siteBalloonHelp" """
        
        bat """heat dir "${HelpDir}\\spotlightHelp_site" -o "${HelpDir}\\helpsite.wxs" -scom -sfrag -srd -sreg -gg -cg HelpSiteGroup -dr HELP_SITE_DIR """
        bat """heat dir "${HelpDir}\\spotlightHelp_siteBalloonHelp" -o "${HelpDir}\\balloonhelpsite.wxs" -scom -sfrag -srd -sreg -gg -cg BalloonHelpSiteGroup -dr BALLOONHELP_SITE_DIR """
        
    }
    catch (any)
    {
        emailext recipientproviders: [[$class: 'CulpritsRecipientProvider']], to: 'steven.taylor@software.dell.com', subject: "Spotlight Online Help build failed", body: "See ${env.BUILD_URL} for full details"
        throw any
    }
}

return this;
