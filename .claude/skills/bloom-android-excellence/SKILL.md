---
description: Build, debug, review, refactor, and optimize Bloom’s
  Android app in Kotlin. Follow Bloom’s pregnancy-product flows,
  Dawn/Night/Quiet themes, trimester color system, inclusive
  localization and RTL, privacy promises, and clinician-reviewed
  medical-content boundaries. Inspect the actual repository first and
  verify meaningful changes on Android devices.
name: bloom-android-excellence
---

# Bloom Android — product-aware production engineering

## Mission and scope

Build the best practical production implementation of **Bloom**, an
Android-first pregnancy companion app. This is a **Bloom-specific**
skill, not a generic Android template and not a skill for the
developer’s other applications.

Use it when implementing features, fixing defects, improving UI/UX,
refactoring, reviewing, or optimizing Bloom’s Android application.

The result must be correct, calm, accessible, private, maintainable,
reliable, and efficient on real Android devices. Product quality
includes both engineering correctness and emotional safety.

### Priority order

1.  **User safety, medical-content integrity, privacy, and data
    integrity.**
2.  **Correct product behavior and complete user journeys.**
3.  **Bloom design fidelity, accessibility, inclusion, and emotional
    care.**
4.  **Simple, maintainable, idiomatic Kotlin and Android architecture.**
5.  **Responsive performance, battery and memory efficiency.**
6.  **Measured optimization.** Prefer evidence over cleverness,
    folklore, and extra dependencies.

**Golden rule:** choose the simplest Android-idiomatic implementation
that satisfies the actual requirement, preserves correct behavior,
respects Bloom’s product principles, and is straightforward to test.
Fewer lines or more abstractions do not automatically mean better code.

### Greenfield project baseline

This repository starts as a **new Android Studio project**. Its initial
`MainActivity.kt` may display `Greeting("Android")`; its theme may still
contain generated Material purple colors and `dynamicColor = true`; and
its test source sets may contain `ExampleUnitTest` or
`ExampleInstrumentedTest`. These are normal starter artifacts, not
application defects, regressions, or evidence that a Bloom feature is
broken. They indicate that product implementation has not yet replaced
the template.

- Do not report the starter greeting, generated theme, missing product
  screens, or template tests as defects merely because they are still
  present in an untouched scaffold.
- Build Bloom from the approved design system and full-flow reference.
  Replace starter UI and theme code as the relevant product work is
  implemented; do not pretend the prototype is already implemented.
- Do not assume existing navigation, domain models, persistence, APIs,
  authentication, notifications, billing, analytics, sharing, or
  backend services. Inspect the repository and introduce only what an
  approved feature actually requires.
- Do not import code, architecture, endpoints, conventions, or product
  behavior from unrelated applications.
- Keep the initial project lean. Do not add layers, frameworks, or
  dependencies speculatively; let real feature requirements justify
  structure.
- Treat the checked-in Gradle configuration and version catalog as the
  source of truth for installed toolchain and dependency versions.

The developer is experienced with React/TypeScript and less familiar
with Kotlin. Do not imitate React at the expense of correct Kotlin.
Explain only non-obvious Kotlin/Android decisions with a short
React/JavaScript analogy.

## 1. Product references and source of truth

The canonical product references are committed in this repository:

- **`ui-instuctions/Bloom Design System.html`** — visual and interaction
  design authority. It is version 1, dated 13 November 2026,
  Android-first.
- **`ui-instuctions/Bloom full app flow.html`** — a 21-screen visual
  product-flow reference.

Read these files before implementing or changing Bloom UI and product
flows. If a particular task workspace does not contain the files or
they cannot be accessed, use this skill’s summarized requirements and
state that the source references were unavailable. Do not assume that
an old attachment or remembered copy is more current than the files
committed to the target branch.

Keep two kinds of truth separate:

- **Implementation truth:** the repository tells you what the app
  currently does, which libraries and APIs it uses, and how it is built.
- **Product truth:** the latest explicit user/product requirements and
  approved product/clinical documentation tell you what it should do.
  The Bloom design system defines visual rules; the full-flow HTML
  illustrates screens and representative journeys.

When sources conflict: 1. Follow the developer’s latest explicit
instruction. 2. Follow approved product and clinician-reviewed
requirements for behavior and medical content. 3. Follow the Bloom
Design System for visual language and interaction standards. 4. Use the
full app flow as a screen/flow reference. 5. Inspect existing
implementation to understand current behavior, but do not preserve a
defect merely because it exists.

Do not silently reconcile important contradictions. Explain the conflict
and ask one focused question if a safe, correct implementation cannot be
chosen.

### Important limits of the supplied design references

- The full-flow HTML is a **design prototype/reference**, not a complete
  backend contract, database schema, clinical specification, or
  exhaustive interaction specification.
- Text such as `18 weeks, 3 days`, `14 March 2027`, `8 weeks, 2 days`,
  and `32 weeks, 4 days` is example content. Never hard-code these as
  production defaults.
- `[YOUR PRICE]` and `[TRIAL LENGTH]` are unresolved placeholders. Never
  invent a price, trial length, billing terms, or subscription behavior.
- A screen title or visible button does not define all its edge cases.
  Inspect the code and approved requirements before implementing
  behavior.
- Do not invent postpartum screens, medical guidance, notification
  schedules, partner permissions, or other product rules not established
  by the project.

## 2. Bloom product model and screen inventory

Bloom is a calm pregnancy companion. The supplied flow covers
onboarding, daily check-ins, pregnancy-week information, tracking tools,
planning, journaling, optional partner sharing, privacy controls,
premium, pausing/ending tracking, pregnancy loss, and a baby-arrived
transition.

The 21 reference screens are:

1.  **Language** — welcome, Bloom introduction, English and Urdu
    choices.
2.  **Welcome and due date** — due-date entry and on-device privacy
    reassurance.
3.  **Name and label** — optional nickname and user-selected labels.
4.  **Today** — trimester context, current pregnancy week/day, daily
    mood check-in, and shortcuts to Kicks, Contractions, and Note.
5.  **Check-in done** — confirmation of the saved check-in and a gentle
    no-guilt message about days with no entry.
6.  **Week story** — week-specific content, add-to-questions action, and
    next step.
7.  **Tools** — Kick counter, Contractions, Questions, and Hospital bag.
8.  **Kick counter** — elapsed session time, kick count, tap action, and
    end-session action.
9.  **Kick summary** — session total and duration, option to share with
    a midwife, and Done.
10. **Contraction timer (Night)** — dark/night experience, elapsed
    contraction time, last gap, stop action, and a reserved area for
    reviewer-written midwife guidance.
11. **Plan** — week calendar, appointment details, questions to ask, and
    checklist.
12. **Journal** — dated notes/check-ins and New note.
13. **Together** — optional partner-oriented content, appointments and
    checklist sharing, with mood check-ins explicitly private.
14. **Me and privacy** — app lock, notification-text privacy, discreet
    mode, data export, delete-all, language, labels, and pause/end
    tracking.
15. **Premium sheet** — Bloom Plus offer. The reference says core
    tracking, kick counter, contraction timer, privacy tools, and loss
    support remain free; Plus adds extra week stories and partner
    sharing. Price and trial values are unresolved placeholders.
16. **Pause or end tracking (Quiet theme)** — pause updates/reminders,
    pregnancy loss, changed situation, or delete everything.
17. **Loss: gentle confirmation** — confirmation that pregnancy
    updates/reminders stop, data remains on the phone, and the user may
    keep private notes, access reviewed support resources, or delete
    everything.
18. **Baby has arrived** — switch to postpartum mode, archive this
    pregnancy, or skip.
19. **Today, trimester 1** — Today state using Lagoon.
20. **Today, trimester 3** — Today state using Dusk.
21. **Today in Urdu (RTL)** — localized Today screen with right-to-left
    layout.

### Main navigation and known journeys

The bottom navigation is **Today, Journal, Tools, Plan**, each with an
icon and visible text label. **Me/Profile** is opened from the
avatar/profile button, not added as a fifth bottom-navigation item
unless a newer approved requirement says otherwise.

Known journeys illustrated by the reference:

- First run: Language → due date → name/label → Today.
- Daily check-in: Today mood selection → check-in confirmation → Today.
- Pregnancy information: Week story → optionally add a question → Next.
- Tracking: Tools or Today shortcut → Kick counter → Kick summary.
- Contractions: Tools or Today shortcut → Contraction timer in Night
  theme.
- Planning: Plan → calendar/appointment → questions → checklist.
- Journaling: Journal → existing entries → New note.
- Sharing: Together → manage what is shared. Mood check-ins remain
  private.
- Profile/privacy: Me → app/privacy preferences, language, labels,
  export/delete, pause/end.
- Pause/loss: Me or the relevant entry point → Quiet pause/end choices →
  loss confirmation when selected.
- Baby arrived: baby-arrived transition → postpartum mode, archive, or
  skip.

Treat this as the known reference, not as proof that every transition,
persistence rule, or edge case has been fully specified. Trace the real
implementation and use current product requirements for missing
behavior.

### Inclusive labels and relationships

The prototype shows optional nickname and selectable labels such as Mum,
Mama, Parent, No label, Your partner, Partner, Husband, and Wife. These
are user choices, not assumptions about the person using the app.

- Never assume the user is a mother, uses a particular title, has a
  partner, or has a husband/wife.
- Preserve user-selected labels throughout the app.
- Keep partner relationship language neutral unless the user explicitly
  selected a label.
- Do not generalize a gendered sample sentence from the prototype into
  fixed copy. Reconcile sample copy with Bloom’s inclusive product
  principle.
- Make optional fields genuinely optional when the product flow says
  they are optional.

## 3. Bloom’s product principles

Apply these principles to every feature, screen, notification, error,
and empty state.

### Worst day first

Design for someone who is tired, using one hand, awake at 3 a.m.,
anxious, overwhelmed, or experiencing pregnancy loss. The app must
remain understandable and gentle under these conditions.

- Avoid time pressure, unnecessary steps, dense screens, tiny targets,
  and punitive language.
- Put the primary action where it is easy to reach.
- Make destructive or emotionally significant choices clear without
  making the user feel guilty.
- Do not use celebratory language in contexts where it may be painful.

### One focal point

- One main action per screen.
- Do not give several controls equal visual weight when one is the
  natural next step.
- Keep secondary actions available but visually secondary.
- Avoid adding cards, banners, or calls to action merely to fill space.

### No guilt

- No streaks, scores, rankings, shame, or red warning treatment for
  missed logging.
- Never imply that the user failed by not checking in, counting kicks,
  or writing a journal entry.
- Use calm, factual copy. Example: “Nothing logged yesterday. That’s
  fine.”
- Never congratulate or reward pregnancy tracking as if it were a
  performance.

### Visibly private

The Bloom Design System explicitly specifies: **“Stored on this phone.
No ads.”** The full-flow HTML is a separate prototype reference. If it
contains additional or different privacy wording, preserve the source
distinction and surface the discrepancy rather than silently combining
or expanding the promise. Use the wording approved for the screen being
implemented.

Treat every privacy statement as a product promise that the
implementation must actually support: - Inspect persistence, network calls, analytics, crash
reporting, backups, exports, and third-party SDKs before claiming data
is local-only. - Do not add ads, tracking SDKs, cloud sync, telemetry,
or new sensitive-data transmission without explicit product approval and
accurate user disclosure. - Do not show a privacy claim that is
contradicted by the implementation. - Never put pregnancy details, mood,
notes, or other sensitive content into logs, analytics events, crash
breadcrumbs, or notification previews without explicit approval. -
Privacy controls must have real behavior, not decorative switches.

### Inclusive and local

- Let the user choose labels and language.
- Do not infer identity, family structure, partner gender, or preferred
  form of address.
- Treat localization, RTL, date formats, numeral choices, and script
  rendering as product requirements, not a later polish pass.

## 4. Medical-content safety and emotional safety

Bloom is a pregnancy companion, not a diagnostic or triage service.
Engineering work must not turn tracking tools into medical
decision-makers.

### Non-negotiable clinical boundaries

- Do not create, improvise, paraphrase into a stronger claim, or
  hard-code pregnancy warning signs, emergency thresholds, medical
  instructions, diagnosis, treatment, or reassurance.
- Medical and warning-sign content must come from a clinician or
  qualified medical reviewer approved by the product owner.
- Translations of medical content must be reviewed by a qualified
  reviewer. Machine-only translation is not acceptable for medical
  content.
- If approved content is missing, leave a clearly identified content
  dependency and report it. Do not fill the gap with general medical
  knowledge.
- Do not state that a kick count, contraction interval, symptom, or
  pregnancy-week value is “normal,” “safe,” or “dangerous.”
- Do not make a medical recommendation from app data. Do not add a risk
  score or predictive interpretation.
- Preserve approved medical wording and its context. Do not truncate,
  reorder, hide, or weaken clinically important text.

### Kick counter

- The tool records taps, elapsed time, and a session summary.
- It is a recording tool, not a test that decides whether the baby is
  healthy.
- Do not invent kick-count targets, thresholds, alerts, or reassurance.
- The reference wording says: “Your midwife can tell you what is usual
  for you.”
- Share only the data the user explicitly chooses to share.
- Make the session resilient to lifecycle changes and accidental double
  taps without fabricating or losing recorded taps.

### Contraction timer

- Record start/stop times and elapsed gaps accurately; do not interpret
  them as a diagnosis or labor prediction.
- Do not invent contraction thresholds or advice.
- Any midwife guidance or warning text must be supplied by the approved
  medical reviewer.
- The Night theme is deliberate: keep the experience calm, legible, and
  usable at night.
- Respect haptics and reduce-motion settings.

### Pregnancy loss and changed circumstances

The loss flow is a first-class product journey, not an edge case.

- Use the **Quiet** theme for pause/loss flows.
- Stop pregnancy updates and reminders when the loss flow confirms that
  behavior.
- Do not show premium upsells, cross-promotion, streaks, celebrations,
  or trimester season colors in Quiet.
- Do not make the user justify the decision or repeatedly confirm that
  they want to stop.
- Preserve data on the phone when the reference says it remains there.
  Delete only when the user explicitly chooses deletion and confirms the
  destructive action.
- Offer private notes and reviewed support resources only as specified
  by the product.
- Never invent grief-support or crisis-resource content. Use approved,
  region-appropriate resources.
- Verify that scheduled notifications, workers, alarms, and reminders
  are actually cancelled and cannot unexpectedly reappear.

### Baby-arrived transition

- Offer the documented choices: switch to postpartum mode, archive the
  pregnancy, or skip.
- Do not assume the user wants to celebrate, share, or immediately
  switch modes.
- Do not invent postpartum functionality that is not documented or
  implemented.

## 5. Bloom design system — implementation rules

The design system is the visual authority. Do not create a parallel
theme or substitute generic Material defaults for Bloom’s identity.

### Themes

**Dawn** is the default theme. The trimester season color follows the
user’s current trimester.

**Night** is used automatically where configured and has a one-tap
switch on the contraction timer. It uses dark surfaces and never pure
white.

**Quiet** is forced for pause and loss flows. It uses neutral stone
colors, no trimester season color, no decorative motion, no upsell, and
no cross-promotion.

Android Studio's generated Compose theme may enable dynamic color and
use generic Material colors. Those are starter defaults, not Bloom
design decisions. Implement Bloom's explicit color tokens for product
screens and keep dynamic color disabled by default. “Match my phone” is
described only as an optional later toggle; implement it only after
explicit product approval, and never apply it to Quiet.

### Color tokens

| Token   | Value     | Use                                      |
|---------|-----------|------------------------------------------|
| Paper   | `#FFF8F3` | Dawn background                          |
| Surface | `#FFFFFF` | Cards and surfaces                       |
| Raised  | `#F7EBE4` | Raised surface                           |
| Ink     | `#2B2024` | Main text                                |
| Muted   | `#6B5B61` | Secondary text                           |
| Error   | `#A3263A` | Error, always paired with icon and words |

Trimester season colors (primary / tint / deep):

| Season  | Trimester | Primary   | Tint      | Deep      |
|---------|-----------|-----------|-----------|-----------|
| Lagoon  | T1        | `#1F7A78` | `#DDF1EE` | `#14504E` |
| Apricot | T2        | `#B4531A` | `#FFE6D2` | `#7A3510` |
| Dusk    | T3        | `#6E3B8E` | `#EBDDF5` | `#4A2562` |

Use tints for large shapes and primary colors for buttons and key text.
Do not rely on color alone to communicate state.

Quiet: - Background: `#E9ECEF` - Accent: `#5E666F` - No trimester color
or decorative motion.

Night: - Background: `#1C1519` - Surface: `#271D23` - Raised:
`#33262D` - Text: `#F2E6EA` - Muted: `#C9B8BF` - Accent options:
`#7FD3CC`, `#FFB68A`, `#D3B3F0` - No pure white.

Contrast: - At least 4.5:1 for normal text. - At least 3:1 for large
text and icons. - Check every actual theme/season combination, including
disabled, focused, selected, and error states.

### Typography

Latin: - Display: Fraunces 600, 56/60. - Headline: Fraunces 600,
28/34. - Title: Figtree 600, 20/26. - Body: Figtree 400, 16/24. - Label:
Figtree 600, 14/20.

Use the project’s bundled/installed font assets and verify
licensing/weights. Do not silently replace the intended fonts with a
visually unrelated typeface.

Urdu: - UI: Noto Naskh Arabic. - Display: Noto Nastaliq Urdu for large
text only. - Add extra line height. - Do not rely on wrap-content boxes
for Nastaliq text. - Test clipping, shaping, baseline, and layout on
low-end devices.

Other scripts must use the appropriate script font from the design
system and project resources. Do not claim support for a language until
the resources and rendering are actually implemented.

Support Android font scaling up to 200% without clipping or loss of
actions.

### Spacing, layout, and shapes

- Use a 4-point spacing grid: 4, 8, 12, 16, 20, 24, 32 dp.
- Reference side margins are 20 px on a 390 px screen; adapt
  responsively rather than hard-coding a device width.
- Radius scale: 12, 20, 28, 40, and full pill.
- Cards: 28 dp radius, white Dawn surface, one idea per card. The Today
  screen has only one Today card at a time.
- Chips: 20 dp radius, 14/20 label.
- Bottom sheets: 32 dp top radius.
- Onboarding and Week stories: 4 dp progress segments.
- Use soft tinted elevation/shadows, based on the season’s deep tone;
  avoid harsh generic shadows.
- The Bloom mark and organic shapes are part of the visual identity. Do
  not distort or mirror the Bloom shape.

### Components and states

- Primary button: pill shape, 56 dp high.
- Secondary button: at least 48 dp high.
- One primary action per screen.
- Inputs: 56–60 dp high, 2 dp outline, visible label above the field.
  Never use placeholder-only labeling.
- Interactive targets: at least 48 dp with 8 dp spacing.
- Primary actions should be reachable in the bottom 40% of the screen
  where the layout allows.
- Mood choices: five labeled shapes, 48 dp, one tap, no score. A
  shape/color must never be the only way to identify a mood.
- Focus: visible 3 dp outline.
- Disabled: 60% treatment with a clear disabled state/label; do not make
  important content unreadable.
- Error: icon + words + error color, never color alone.
- Pressed/selected shapes may morph from circle to flower; motion must
  remain subtle and accessible.
- Navigation items always include both icon and text label.
- Use reusable Bloom components/tokens. Avoid slightly different button,
  input, chip, card, and sheet styles across screens.

### Motion, haptics, icons, and illustration

- Spring motion: approximately 200–400 ms for presses, selections, and
  sheets.
- Bloom morph: approximately 600 ms.
- At most one slow ambient animation; all other animation responds to
  user action.
- Respect system reduce-motion: replace movement with fades and stop
  ambient animation.
- No flashing. Sound is off by default.
- Soft haptic feedback is appropriate for kick taps and contraction
  start/stop. Never use haptics for marketing.
- Icons: rounded 2 dp stroke, 24 dp. Navigation icons always have
  labels.
- Illustration: flat, grainy shapes. No faces, skin tones, gendered
  colors, or stock baby photos.
- Baby-size food illustrations should be regional and appropriately
  licensed/commissioned.

### Voice and copy

Prefer: - “You’re 18 weeks and 3 days.” - “Nothing logged yesterday.
That’s fine.” - “Ask your midwife about this.” - “Take whatever you
need.”

Avoid: - “Amazing job tracking!” - “You broke your streak.” - “This
could be dangerous.” (unless this exact warning is approved medical
copy) - “Are you sure you want to stop?”

Keep copy calm, concise, factual, non-judgmental, and inclusive. Do not
use shame, pressure, guilt, or unsupported reassurance.

## 6. Localization and RTL are core requirements

The design system requires strings in Android resources; do not
hard-code visible text in composables, Activities, Fragments, adapters,
or business logic.

- Use Android resource localization and the project’s established
  localization approach.
- Use ICU message formatting for plurals and gender-sensitive phrasing
  where appropriate.
- Format dates, numbers, week/day values, and calendars using the
  selected locale and the user’s numeral/calendar preferences.
- Use logical `start`/`end`, not hard-coded `left`/`right`.
- In RTL, mirror layout direction, navigation order, progress bars, and
  back arrows.
- Do **not** mirror numerals, the Bloom shape, or media icons.
- Allow 30–40% longer translated text. Avoid fixed-width buttons and
  clipped labels.
- Urdu and other script fonts need correct shaping, baseline, line
  height, and font fallback.
- The language switch is available during onboarding and in Me, and
  applies immediately. Respect Android per-app language settings where
  supported by the actual target SDK.
- Missing translations may fall back to English, but must be detectable
  in tests/build checks; do not silently ship missing medical
  translations.
- Medical text requires qualified human review after translation.
- Baby-size comparisons should use a regional food set appropriate to
  the selected language/region, not a single culturally narrow list.

Test the complete screen, not just isolated strings, in English LTR and
Urdu RTL. Check arrows, back navigation, bottom navigation order,
alignment, text clipping, mixed numerals, dates, and accessibility focus
order.

## 7. Inspect the Android project before coding

Before changing files, establish what Bloom actually uses:

- Read `AGENTS.md`, `CLAUDE.md`, `.claude/`, `.agent/`, existing skills,
  module guidance, and relevant feature documentation.
- Examine module layout, `settings.gradle(.kts)`, root/module
  `build.gradle(.kts)`, version catalog, `gradle.properties`,
  `AndroidManifest.xml`, source sets, and tests.
- Identify Kotlin, AGP, Gradle, Java toolchain, compile/target/min SDK,
  Compose compiler/BOM (if present), build variants, and installed
  dependencies from files. Never guess versions.
- Determine whether the UI uses Jetpack Compose, Views/XML, or a
  mixture.
- Identify navigation, dependency injection, networking, local
  persistence, notifications/reminders, background work, image loading,
  billing, and test frameworks.
- Trace the affected journey end-to-end: entry point → screen → state
  owner → domain logic → repository → local/remote data → notifications
  or sharing if relevant → error handling → tests.
- Inspect callers and persisted data before changing public APIs,
  database schemas, navigation, or serialization.
- Read `git status` and the current diff. Preserve unrelated user work.
  Never reset, revert, overwrite, reformat, rename, or rewrite unrelated
  files.
- Identify the existing theme and component system before creating UI.
  Do not add a second design system.
- Do not claim a dependency, architecture, or performance issue without
  inspecting evidence.

Default behavior: implement the requested task without unnecessary
permission requests. For broad or risky work, plan briefly, then execute
in independently verifiable steps. If the user explicitly asks for
planning only, do not edit files.

## 8. Kotlin style: idiomatic, boring, predictable

- Follow official Kotlin conventions and the project’s formatter/linter.
- Prefer `val`, immutable DTOs/UI state, read-only collection
  interfaces, and narrow mutation ownership.
- Use descriptive `PascalCase` types and `camelCase` members.
- Prefer straightforward functions, data classes, and sealed types for
  genuinely distinct states.
- Avoid `!!`, unsafe casts, magic strings, hidden assumptions, and broad
  exception swallowing.
- Use nullable types only when null is a real domain possibility.
- Keep APIs narrow with `private`/`internal`.
- Use scope functions only when they make receiver and intent clearer.
  Avoid nesting them.
- Avoid abstraction for abstraction’s sake: unnecessary interfaces,
  generic frameworks, factories, managers, utility classes, DSLs,
  reflection, or single-use helper files.
- A small helper is justified when it gives a meaningful name, test
  seam, compatibility boundary, or removes real duplication.
- Keep related declarations together; split by responsibility, not
  arbitrary line count.
- Comments explain why, lifecycle decisions, invariants, or
  limitations—not what obvious code already says.
- Prefer standard library APIs. Consider allocations and complexity for
  hot paths; do not assume `Sequence` is always faster.
- Avoid deprecated and experimental APIs unless a verified project
  constraint requires them.
- Do not upgrade dependencies, Gradle, AGP, Kotlin, SDK levels, or
  enable experimental flags opportunistically.

### React/TypeScript analogies for non-obvious concepts

| Kotlin / Android        | Approximate React / JavaScript analogy | Critical difference                                          |
|-------------------------|----------------------------------------|--------------------------------------------------------------|
| `@Composable`           | React function component               | Compose has its own recomposition and snapshot state model.  |
| `remember`              | `useMemo` / `useRef`                   | Lifetime is tied to the current composition.                 |
| `rememberSaveable`      | Restorable local UI state              | Uses saved-state mechanisms; keep payloads small.            |
| `mutableStateOf`        | `useState`                             | Observable Compose snapshot state, not React’s setter model. |
| `ViewModel`             | Screen-level state controller          | Survives configuration changes, not process death.           |
| `StateFlow`             | Observable store / BehaviorSubject     | Collection must respect Android lifecycle.                   |
| `Flow`                  | Async stream                           | Cold streams may repeat upstream work on each collection.    |
| `suspend` / coroutine   | `async` / `await`                      | Structured cancellation and scope ownership matter.          |
| `viewModelScope`        | Scoped async tasks                     | Cancelled when the ViewModel is cleared.                     |
| `LazyColumn(key = ...)` | List with stable React `key`           | Keys also affect Compose item state and slot reuse.          |
| Repository              | API/data service plus cache boundary   | Owns consistent access to data sources.                      |
| `SavedStateHandle`      | Restorable screen parameters           | Small state only; not a database.                            |
| `WorkManager`           | Durable queued background job          | OS-scheduled; not an exact timer.                            |

Do not explain every line. Explain only concepts or trade-offs the
developer is unlikely to recognize.

## 9. Architecture: lean, with a clear source of truth

Use the architecture already established by the repository. A typical
shape may be:

`UI (Compose/Views) → screen state owner (usually ViewModel) → repository → local database / API / Android service`

- Separate rendering, state/event handling, and data access.
- Follow unidirectional data flow: state flows down; user actions flow
  up.
- UI should not directly call DAOs, HTTP clients, DataStore, or platform
  services when the project has a repository boundary.
- Use ViewModels for screen/business state when beneficial. Obtain them
  at screen/navigation boundaries; reusable UI components receive data
  and callbacks.
- Expose read-only state (`StateFlow`, `Flow`, or immutable UI models);
  keep mutable implementation details private.
- Represent loading, content, empty, and failure states explicitly.
- Use a domain/use-case layer only for substantial reusable business
  logic or to simplify complex state owners.
- Reuse the existing DI strategy. Do not add another DI framework.
- Define one source of truth for each data type. Keep Today, Journal,
  Plan, and Together consistent through repositories rather than
  separate conflicting copies.
- Avoid duplicated flags when state can be derived.
- Do not assume the server is authoritative for local features or that
  the app is offline-first without checking product and repository
  behavior.

### Compose-specific rules

Apply only if the project uses Jetpack Compose.

- Keep composables focused and side-effect-free during rendering.
- Hoist state to the lowest common owner that needs it.
- Keep local presentation state local; use ViewModel/repository state
  for business data.
- Use `rememberSaveable` for small UI state that should survive Activity
  recreation; use `SavedStateHandle` for small restorable ViewModel
  inputs.
- Do not store important pregnancy records only in composition state.
- Use `LaunchedEffect` with correct keys for composition-bound suspend
  effects; `DisposableEffect` for subscriptions requiring cleanup.
- Never start network/database work during ordinary composition.
- Collect shared screen state at the appropriate boundary with
  lifecycle-aware collection. Avoid collecting the same cold Flow
  independently in every row.
- Keep rapidly changing state reads low in the UI tree.
- Do not add `remember`, `derivedStateOf`, `@Stable`, `@Immutable`,
  custom equality wrappers, or manual callback memoization everywhere.
  Measure first.
- Use immutable collections/state updates; do not mutate a list in place
  and expect Compose to observe it.
- Use lazy containers for large/scrolling data, stable unique saveable
  keys, and `contentType` where layouts differ. Small fixed groups can
  use regular `Row`/`Column`.
- Do not sort/filter large lists, parse JSON, decode bitmaps, or perform
  expensive formatting during every recomposition.
- Use the existing image loader and appropriately sized images.

### Views/XML compatibility

If the project uses Views, RecyclerView, Fragments, or mixed UI, do not
rewrite it into Compose unless explicitly requested.

- Use ViewBinding or the existing binding strategy.
- Use `ListAdapter`/`DiffUtil` or the existing equivalent for changing
  lists.
- Collect Flows with `repeatOnLifecycle` or equivalent.
- Release view references and listeners at the correct Fragment
  lifecycle boundary.
- Avoid repeated observers, redundant adapter setup, expensive
  measure/layout nesting, and main-thread bitmap work.

## 10. Lifecycle, timers, dates, and reminders

Bloom has time-sensitive pregnancy and tracking UI. Treat time and
lifecycle correctness as product correctness.

### Pregnancy dates and week/day display

- Inspect the repository’s current pregnancy-date calculation and the
  approved product rule before changing it.
- Keep date math in a testable domain layer, not scattered through UI
  code.
- Handle time zones, daylight-saving changes, locale calendars, leap
  years, and date boundaries.
- Test due-date changes and week/day transitions at boundaries.
- Derive displayed weeks/days from the user’s real pregnancy data. Never
  ship prototype sample values as defaults.
- Do not make clinical claims based on a calculated week/day value.

### Kick and contraction sessions

- Use a monotonic time source for elapsed duration; do not calculate
  session duration by subtracting wall-clock timestamps alone.
- Define and test pause/resume, process recreation, duplicate taps,
  stop/end, and persistence behavior.
- Avoid losing recorded events on rotation, backgrounding, or process
  death when the product expects a session to continue.
- Do not allow stale callbacks or multiple collectors to double-count
  events.
- Use the project’s existing haptic implementation and respect system
  settings.

### Notifications and background work

- Identify every source of reminders: alarms, WorkManager, notification
  scheduling, receivers, services, and server messages if present.
- Pausing or ending tracking must stop the relevant pregnancy
  updates/reminders as specified.
- On loss confirmation, cancel pending work and ensure it cannot be
  rescheduled by stale state or app restart.
- Do not invent reminder frequency, notification content, or exact
  delivery guarantees.
- Keep sensitive pregnancy details out of notification previews when
  hide-notification-text/discreet mode is enabled.
- Use WorkManager for durable deferrable work when appropriate; do not
  treat it as an exact timer.
- Avoid duplicate workers, polling, wake locks, and unnecessary
  background activity.

## 11. Persistence, privacy, sharing, and data deletion

- Follow the actual storage contract. The design says data is stored on
  this phone; verify database, files, preferences, backups, network
  requests, and third-party SDKs before making or changing that promise.
- Use the existing storage library. Room is appropriate for structured
  relational records when the app uses it; DataStore is for small
  preferences. Do not introduce either without inspecting the project.
- Use transactions for related writes and migrations for schema changes.
  Test migration paths from supported installed versions.
- Never log notes, mood, pregnancy details, personal labels,
  identifiers, access tokens, or complete sensitive payloads.
- Keep app lock, hide notification text, and discreet mode behavior real
  and testable.
- Together sharing must be explicit and scoped. The prototype
  specifically states that mood check-ins stay private. Do not expose
  mood data through partner views, notifications, exports, logs, or
  shared summaries.
- Do not broaden partner access to all user data by convenience.
- Export only the data the user expects and has chosen to export.
  Protect temporary export files and clean them up appropriately.
- Delete everything must cover all relevant local databases, files,
  preferences, cached sensitive content, and scheduled work. Verify the
  result; do not leave a hidden copy in app-owned storage.
- Confirm destructive deletion clearly and accessibly. Never delete data
  just because tracking is paused or ended.
- Do not add cloud sync or remote backup without explicit product
  approval and transparent consent.

## 12. Networking, billing, and offline behavior

- Follow existing HTTP client, serialization, API contracts, auth, and
  error format. Do not add a preferred networking library just because
  it is familiar.
- Do not let each child component or list row trigger its own request.
- Avoid read-after-write requests when a mutation response contains the
  authoritative updated data and the repository can update its source of
  truth consistently.
- Keep loading, empty, offline, network failure, retry, and unauthorized
  states explicit.
- Use pagination for potentially large datasets.
- Add caching, expiration, invalidation, or conflict handling only when
  requirements justify them.
- Retry transient failures carefully. Do not blindly retry
  non-idempotent writes.
- Validate untrusted input at boundaries. Never weaken TLS or expose
  credentials.
- The design reference says core tracking, kick counter, contraction
  timer, privacy tools, and loss support are always free. It describes
  extra week stories and partner sharing as Bloom Plus features.
- Inspect the actual billing implementation and current approved
  monetization requirements before changing entitlements.
- Do not invent price, trial length, billing period, cancellation
  policy, or entitlement behavior. The design’s `[YOUR PRICE]` and
  `[TRIAL LENGTH]` are placeholders.
- Do not display premium sales content in Quiet, pause, or loss
  journeys.

## 13. Performance, memory, startup, and battery

- No blocking network/database/file IO, large JSON transformations,
  bitmap decoding, or synchronous waits on the main thread.
- Keep `Application`/`Activity.onCreate` minimal and defer nonessential
  initialization.
- Avoid eagerly loading screens/data that the user has not opened.
- Do not retain Activity/View/Context references in long-lived
  ViewModels, repositories, static objects, or callbacks.
- Avoid unnecessary polling, wake locks, continuous sensors/location,
  redundant connections, and eager sync.
- Use event-driven updates and OS scheduling where appropriate.
- Avoid N+1 database/API calls, duplicate requests, repeated Flow
  collection, redundant observers, unnecessary recomposition, expensive
  rendering transformations, and repeated bitmap decoding.
- Optimize release builds with the project’s existing R8/shrinking
  setup. Do not trade correctness or library compatibility for APK size.
- Consider Baseline Profiles/Macrobenchmark for proven critical
  journeys; do not add benchmark infrastructure without a clear benefit.
- Do not claim “60 FPS,” “zero leaks,” “fully optimized,” or “no
  crashes” without evidence.

### Performance proof standard

1.  State the suspected bottleneck and why.
2.  Capture a baseline when feasible (trace, profiler, benchmark, query
    log, request count, allocations).
3.  Apply the smallest meaningful improvement.
4.  Compare before/after under similar conditions.
5.  Report measured values with device/build/test conditions, or
    explicitly say the change was not measured.

Use Android Studio Profiler, System Trace/Perfetto, Macrobenchmark,
Microbenchmark, and Android vitals where relevant.

## 14. UI/UX verification and accessibility

UI/UX is part of correctness. Do not stop at “it compiles.”

Verify the affected screen and its complete journey:

- Match the Bloom design system’s color, typography, spacing, radius,
  elevation, components, states, and motion.
- Check the primary action hierarchy and one-handed reachability.
- Respect status/navigation bars, display cutouts, edge-to-edge insets,
  gesture navigation, and keyboard/IME.
- Check small phones, large phones, supported tablets, and landscape if
  the app supports it.
- Test font scaling through 200%, long text, empty states, error states,
  and disabled/focused/pressed states.
- Test TalkBack labels, logical focus order, touch target size, and
  contrast in each theme.
- Test English LTR and Urdu RTL, including navigation order, arrows,
  dates, numerals, text shaping, and clipping.
- Check Dawn for each trimester, Night, and Quiet where relevant.
- Verify reduce-motion, ambient animation, sound-off default, and haptic
  behavior.
- Avoid native alert dialogs if Bloom already has a custom dialog/sheet
  pattern; reuse the established component.
- Use screenshot/golden comparison for visual regression when the
  project has a suitable setup. Compare like-for-like device size,
  density, font scale, theme, and fixture data. Mask or normalize
  dynamic content rather than accepting misleading diffs.
- Use real browser/device or emulator inspection when available; do not
  claim pixel-perfect verification from source inspection alone.

## 15. Testing and verification

Every meaningful change needs tests proportional to its risk.

### Unit tests

- Date/week calculations, locale formatting, state transitions,
  validators, sorting, calculations, timer logic, and pure business
  rules.
- Edge cases: leap day, timezone boundaries, trimester transitions,
  empty data, long text, and invalid input.
- No invented medical thresholds or clinical assertions in tests.

### ViewModel/repository tests

- Success, loading, empty, failure, cancellation, retry, caching,
  persistence, and data consistency.
- Duplicate event prevention and correct state ownership.
- Sharing permission boundaries and mood privacy.
- Pause/loss notification cancellation and delete-all behavior.

### Compose/UI or instrumentation tests

- Onboarding and language switching.
- Daily check-in and navigation.
- Kick counter and summary.
- Contraction timer start/stop and Night theme.
- Plan, journal, and Together permissions.
- App lock, hide notification text, discreet mode, export, and delete.
- Pause/end, loss confirmation, and baby-arrived choices.
- RTL, TalkBack semantics, font scaling, Activity recreation, and
  navigation restoration.

### Integration and device tests

- Database migrations and deletion.
- Notification/worker scheduling and cancellation.
- Export file creation and cleanup.
- Billing entitlement checks if touched.
- Offline/slow network behavior if the feature uses networking.
- Background/resume, process death/restore, permission denial, and
  supported Android versions.

### Visual regression matrix

For a UI change, use the closest available test setup to cover: -
Reference viewport around 390 × 844 dp. - At least one smaller supported
phone and one larger supported phone. - English LTR and Urdu RTL when
the screen is localized. - Dawn and the applicable trimester season. -
Night for the contraction timer. - Quiet for pause/loss. - Default and
200% font scale for screens with meaningful text.

Use actual project build/test commands. Common examples—adapt to the
real module, variants, and configured tasks:

``` bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
./gradlew :app:lintDebug
# If configured: connectedAndroidTest, Compose UI tests, ktlint, detekt, benchmarks
```

Do not claim a command passed unless it was run. Report **passed /
failed / not run**, and why. Do not change signing, release credentials,
dependencies, or unrelated configuration just to get a green build.

## 16. Refactoring and optimization rules

- Preserve observable behavior, stored data, date calculations,
  navigation, permissions, accessibility, privacy behavior,
  notifications, and public APIs unless the change is authorized.
- Prefer eliminating redundant work before adding caching or
  concurrency.
- Optimize algorithms and data access first, then allocations/rendering,
  then micro-level syntax.
- Do not make work concurrent unless it improves the actual task and
  races/cancellation are handled.
- If adding a dependency, abstraction, cache, Flow, CoroutineScope,
  state holder, permission, or build step, justify its ownership,
  lifecycle, invalidation, and payoff.
- Do not rewrite working modules as speculative cleanup.
- Keep diffs small and reviewable. Avoid formatting churn.
- Fix root causes. Do not silence errors, suppress exceptions, remove
  tests, or hide warnings to make checks pass.

## 17. Working protocol for every coding request

1.  **Understand** — identify the requested outcome, affected Bloom
    journey, constraints, and whether any medical/privacy/destructive
    behavior is involved.
2.  **Inspect** — read project instructions, relevant screens, state
    owners, data paths, dependencies, existing tests, and current diff.
3.  **Plan** — for multi-file or risky work, outline a short
    implementation and verification plan. Identify unknown
    product/clinical decisions before coding.
4.  **Choose** — select the simplest sound approach that matches
    existing architecture and Bloom’s design system.
5.  **Implement** — make focused changes, reuse established
    components/tokens, preserve user data, and keep state ownership
    clear.
6.  **Verify** — run relevant build, unit, UI, lint, device,
    accessibility, localization, and visual checks available for the
    change.
7.  **Review the diff** — check for regressions, duplicate
    work/requests, race conditions, lifecycle leaks, unsafe nullability,
    medical-content drift, privacy leaks, theme drift, RTL defects,
    overengineering, and unrelated edits.
8.  **Report** — state what changed, why, tests run and results, what
    was not verified, and any remaining product/clinical dependencies.
    Explain only non-obvious Kotlin decisions with a short React/JS
    analogy.

### Before completion, ask yourself

- Does this solve the requested Bloom user problem end-to-end?
- Did I follow the latest user instruction and approved product/clinical
  requirements?
- Did I preserve data, privacy, and the user’s ability to pause, end,
  export, or delete?
- Did I avoid unsupported medical claims or invented warning guidance?
- Does each piece of state have one clear owner and source of truth?
- Can lifecycle changes, screen re-entry, rotation, or process death
  duplicate work or lose records?
- Are timers based on a suitable monotonic clock?
- Are reminders cancelled when the product says they should stop?
- Does the screen use the correct Dawn, Night, or Quiet treatment?
- Are trimester colors, typography, spacing, shapes, buttons, cards, and
  inputs consistent?
- Does Urdu RTL work without mirrored numerals or Bloom artwork?
- Does it remain usable at 200% font scale and with TalkBack?
- Are errors, empty states, offline states, and double taps handled?
- Did I avoid unnecessary dependencies, wrappers, caches, flags, and
  formatting churn?
- Are tests and performance claims actually supported by evidence?

## 18. Explicit anti-patterns

Do not:

- Treat Bloom as a generic pregnancy template or import product
  assumptions from another app.
- Carry over app-specific behavior, architecture, endpoints, storage, or
  design from the developer’s older applications.
- Replace working Views/XML with Compose or change
  navigation/architecture without a real requirement.
- Impose React hooks, folder conventions, or JavaScript patterns on
  Kotlin.
- Build a massive Clean Architecture/use-case/interface hierarchy for a
  small feature.
- Call APIs or databases from composable rendering or each list row.
- Store pregnancy records only in `remember`, `rememberSaveable`, or
  `SavedStateHandle`.
- Use `GlobalScope`, `runBlocking` on main, or unmanaged long-lived
  listeners.
- Swallow coroutine cancellation or silently ignore errors.
- Add polling, duplicate state mirrors, speculative caches, or
  fetch-again-after-every-save by default.
- Hard-code dates, pregnancy weeks, trimester, prices, trial lengths,
  medical thresholds, warning signs, translated medical text, or fake
  API responses.
- Treat the kick counter or contraction timer as a medical assessment.
- Add streaks, scores, guilt language, ads, or pressure-based
  notifications.
- Expose private mood check-ins or broaden partner access without
  explicit permission.
- Show premium upsells in Quiet, pause, or loss flows.
- Leave reminders active after the confirmed loss/pause behavior
  requires them to stop.
- Make privacy claims that the implementation cannot support.
- Mirror numerals, the Bloom shape, or media icons in RTL.
- Use color as the only state signal or remove visible labels from
  navigation icons.
- Invent clinical, grief-support, or emergency-resource content.
- Refactor unrelated areas, rewrite large files, leave debug logs/dead
  code, or claim success without verification.

## 19. Official references

Consult the current official documentation when changing the relevant
Android feature. Verify every API against the versions actually
installed in the repository.

- Android architecture:
  https://developer.android.com/topic/architecture/recommendations
- Android data layer:
  https://developer.android.com/topic/architecture/data-layer
- Kotlin coding conventions:
  https://kotlinlang.org/docs/coding-conventions.html
- Compose performance:
  https://developer.android.com/develop/ui/compose/performance
- Compose best practices:
  https://developer.android.com/develop/ui/compose/performance/bestpractices
- Compose stability:
  https://developer.android.com/develop/ui/compose/performance/stability
- Android coroutines:
  https://developer.android.com/kotlin/coroutines/coroutines-best-practices
- Saving UI state:
  https://developer.android.com/develop/ui/compose/state-saving
- Offline-first design:
  https://developer.android.com/topic/architecture/data-layer/offline-first
- WorkManager:
  https://developer.android.com/develop/background-work/background-tasks/persistent
- Macrobenchmark:
  https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview
- Baseline Profiles:
  https://developer.android.com/topic/performance/baselineprofiles/create-baselineprofile
- Compose UI testing:
  https://developer.android.com/develop/ui/compose/testing/common-patterns
- Android performance troubleshooting:
  https://developer.android.com/topic/performance/issues

**These references are engineering guidance, not permission to change
Bloom’s product behavior, clinical content, design, or dependencies.**
