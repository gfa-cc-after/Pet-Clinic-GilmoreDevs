import { WebhookClient } from "discord.js";
import { Octokit } from "octokit";

const owner = "gfa-cc-after" as const;
const repo = "Pet-Clinic-GilmoreDevs" as const;

type Config = {
    wehbhookUrl: string;
    githubToken: string;
    repo: string;
    owner: string;
};

type PRContent = {
    id: number;
    title: string;
    body: string | null;
    url: string;
};

const readConfig = (): Config => {
    const wehbhookUrl = process.env.WEBHOOK_URL;
    const githubToken = process.env.GITHUB_TOKEN;

    if (!wehbhookUrl || !githubToken) {
        throw new Error("Missing environment variables");
    }

    return {
        wehbhookUrl,
        githubToken,
        owner,
        repo,
    };
};

const config = readConfig();

const readGitHubPRs = async (config: Config): Promise<PRContent[]> => {
    const octokit = new Octokit({
        auth: config.githubToken,
    });
    const prsOfRepo = await octokit.rest.pulls.list({
        owner: config.owner,
        repo: config.repo,
    });
    return prsOfRepo.data.map((pr) => ({
        url: pr.url,
        id: pr.number,
        title: pr.title,
        body: pr.body,
    }));
};

function sendToDiscord(prs: PRContent[]) {
    const webhookClient = new WebhookClient({
        url: config.wehbhookUrl,
    });
    const content = prs
        .map(({ id, title, body, url }) =>
            `PR #${id}: ${title} at url ${url} with body ${body}`
        )
        .join("\n");

    return webhookClient.send({
        content,
        username: "Pentagon",
        avatarURL: "https://i.imgur.com/AfFp7pu.png",
    });
}

sendToDiscord(await readGitHubPRs(config))
    .then(() => {
        console.log("Successfully sent to Discord");
    }).catch((error) => {
        console.error("Failed to send to Discord", error);
    });
