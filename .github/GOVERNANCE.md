# Kt8s Governance

This document defines the governance policies for the [kt8s](https://github.com/ktform/kt8s) project.

- [Principles](#principles)
- [Code of Conduct](#code-of-conduct)
- [Vendor Neutrality](#vendor-neutrality)
- [Meetings](#meetings)
- [Project Roles](#project-roles)
- [Conflict Resolution](#conflict-resolution)
- [Changes](#changes)
- [Credits](#credits)

## Principles

The Kt8s community adheres to the following principles:

- **Open**: The Kt8s community is open, accessible, and welcoming. Anyone may contribute, and all contributions are available to users under open-source licenses.
- **Transparent**: All project discussions and changes are publicly accessible via GitHub issues, pull requests, and the project's [Discord server](https://discord.gg/s3jyDg5SR6).
- **Merit-based**: Ideas and contributions are evaluated based on their technical merit and alignment with project objectives.

## Code of Conduct

Kt8s follows the [Code of Conduct](CODE_OF_CONDUCT.md), which is aligned with the [CNCF Code of Conduct](https://github.com/cncf/foundation/blob/main/code-of-conduct.md).

## Vendor Neutrality

Kt8s adheres to the vendor neutrality guidelines available at [contribute.cncf.io](https://contribute.cncf.io/maintainers/community/vendor-neutrality/).

## Meetings

Community meetings are held on a defined schedule, published at the [ktform Discord server](https://discord.gg/).

Maintainers may hold closed meetings to discuss security vulnerabilities or Code of Conduct violations.

## Project Roles

The Kt8s community welcomes all contributors and has a well-defined contributor ladder to recognize growth and impact.

### Contributor Ladder

| Role | Description & Requirements | Responsibilities | Defined In |
|---|---|---|---|
| **New Contributor** | New to the project and beginning to engage with the community. | None | N/A |
| **Contributor** | Has made at least five (5) significant contributions. | None | `CONTRIBUTORS.md` |
| **Reviewer** | Makes regular, high-quality contributions and helps triage issues. | Triage & Review | `REVIEWERS.md` |
| **Maintainer** | Owns one or more areas of the project and sets technical direction. | Maintain & Approve | `MAINTAINERS.md` |

#### New Contributors

New contributors are individuals looking to make their first contributions and engage with the community on [Discord](https://discord.gg/s3jyDg5SR6) and GitHub.

#### Contributors

Contributors are individuals who have made at least five documented contributions, including at least three merged pull requests. Other contributions include authoring blog posts, creating tutorials, or actively supporting others in the community.

To become a Contributor, open a pull request adding yourself to the `CONTRIBUTORS.md` file with links to your contributions.

#### Reviewers

Reviewers are experienced contributors who have demonstrated a deep understanding of the codebase and a commitment to the project's success. They are appointed by Maintainers and are responsible for performing detailed code reviews, triaging issues, and mentoring new contributors.

#### Maintainers

Maintainers are technical leaders who have demonstrated exceptional judgment and a long-term commitment to the project. They are responsible for setting the technical direction, approving design decisions, and ensuring the overall health and success of their subprojects.

**Becoming a Maintainer:**

- A candidate must be nominated by a current Maintainer and receive a majority vote.
- The candidate must have a significant history of high-quality contributions and active participation in the community.

**Off-boarding:**

- A Maintainer may be moved to an *emeritus* status if they are inactive for an extended period (e.g., fewer than 30 contributions in 6 months).
- The off-boarding process is initiated by a currently active Maintainer and requires a majority vote.

### Project Areas

Maintainers are not expected to be experts in all areas of the project. Instead, they can specialize in one or more of the following subprojects:

| Project | Description |
|---|---|
| `kt8s-app` | The main application module. |
| `kt8s-client` | The client library for interacting with the Kubernetes API. |
| `kt8s-compiler` | The symbol processor for generating resource classes. |
| `kt8s-controller` | The Kubernetes controller implementation. |
| `kt8s-dashboard` | The web-based dashboard UI. |
| `kt8s-scaler` | The auto-scaling component. |
| `kt8s-ui` | The core UI components. |

## Conflict Resolution

Disputes are expected to be resolved amicably. If a consensus cannot be reached, conflicts will be resolved by a supermajority vote of the project Maintainers.

## Changes

This is a living document. Changes to project governance can be proposed via a pull request and must be approved by a majority of Maintainers.

## Credits

Sections of this document have been adapted from the governance policies of the [CoreDNS](https://github.com/coredns/coredns/blob/master/GOVERNANCE.md) and [Kyverno](https://github.com/kyverno/community/blob/main/GOVERNANCE.md) projects.
